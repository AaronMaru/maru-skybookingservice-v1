package com.skybooking.skygatewayservice.service;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.skybooking.skygatewayservice.utils.AESEncryptionDecryption;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class EncryptionDecryptionService {
    private static final Logger logger = LoggerFactory.getLogger(EncryptionDecryptionService.class.getName());
    @Autowired
    private Environment environment;

    public void decryptRequest(HttpServletRequest request, RequestContext ctx) throws ZuulException {
        try {
            String key = environment.getProperty("encryption.key");
            String initVector = environment.getProperty("encryption.iv");
            var uri = request.getRequestURI();
            InputStream in = (InputStream) ctx.get("requestEntity");
            if (in == null) {
                in = ctx.getRequest().getInputStream();
            }
            String body = StreamUtils.copyToString(in, StandardCharsets.UTF_8);
            logger.info("REQUEST BODY ENCRYPTED STRING: {}", body);

            if (uri.contains("skypoint") && request.getMethod().equalsIgnoreCase("POST") && body.length() > 0
                    && request.getContentType().contains("application/json") && !uri.matches("/skypoint/v1.0.0/top-up/offline/confirm")) {
                JSONObject dataRequest = new JSONObject(body);
                String data = dataRequest.getString("data");
                String decryptedBody = AESEncryptionDecryption.decrypt(data, key, initVector);
                logger.info("REQUEST BODY DECRYPTED STRING: {}", decryptedBody);

                byte[] bytes = decryptedBody.getBytes(StandardCharsets.UTF_8);
                ctx.setRequest(new HttpServletRequestWrapper(getCurrentContext().getRequest()) {
                    @Override
                    public ServletInputStream getInputStream() {
                        return new ServletInputStreamWrapper(bytes);
                    }

                    @Override
                    public int getContentLength() {
                        return bytes.length;
                    }

                    @Override
                    public long getContentLengthLong() {
                        return bytes.length;
                    }
                });
            }
        } catch (Exception e) {
            throw new ZuulException(e, BAD_REQUEST.value(), e.getMessage());
        }
    }
}
