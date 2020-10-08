package com.skybooking.skygatewayservice.service;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.skybooking.skygatewayservice.utils.AESEncryptionDecryption;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.InputStream;
import java.nio.charset.Charset;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class EncryptionDecryptionService {
    private static final Logger logger = LoggerFactory.getLogger(EncryptionDecryptionService.class.getName());


    public void decryptRequest(HttpServletRequest request, RequestContext ctx) throws ZuulException {
        try {
            var uri = request.getRequestURI();
            InputStream in = (InputStream) ctx.get("requestEntity");
            if (in == null) {
                in = ctx.getRequest().getInputStream();
            }
            String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));

            if (uri.matches("/skypoint/v1.0.0/top-up/offline")) {
                String[] data = ((ServletRequestWrapper) request).getRequest().getParameterMap().get("amount");
                String decryptBody = AESEncryptionDecryption.decrypt(data[0]);
                data[0] = "1000";
                HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);
                requestWrapper.setAttribute("amount", 1000);
             //   ((ServletRequestWrapper) request).getRequest().getParameterMap().put("amount", data);
                ctx.setRequest(requestWrapper);

                logger.info("============ request body: {}", ctx.getRequest().getParameterMap().get("amount"));

            } else if (uri.contains("skypoint") && request.getMethod().equalsIgnoreCase("POST") && body.length() > 0) {
                logger.info("REQUEST BODY ENCRYPTED STRING: {}", body);
                JSONObject dataRequest = new JSONObject(body);
                String data = dataRequest.getString("data");
                String decryptBody = AESEncryptionDecryption.decrypt(data);
                logger.info("REQUEST BODY DECRYPTED STRING: {}", decryptBody);

                byte[] bytes = decryptBody.getBytes("UTF-8");
                ctx.setRequest(new HttpServletRequestWrapper(getCurrentContext().getRequest()) {
                    @Override
                    public ServletInputStream getInputStream(){
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
