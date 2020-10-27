package com.skybooking.skygatewayservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.context.RequestContext;
import com.skybooking.skygatewayservice.io.entity.UserEntity;
import com.skybooking.skygatewayservice.io.repository.UserRP;
import com.skybooking.skygatewayservice.model.UserTokenModel;
import com.skybooking.skygatewayservice.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class StakeholderService extends BaseService {

    private static final String POINT_CODE = "SP";

    private static final Logger log = LoggerFactory.getLogger(BaseService.class);

    @Autowired private Environment environment;
    @Autowired private JwtUtils jwtUtils;
    @Autowired private UserRP userRP;

    public void checkUserContact(RequestContext context) {

        if (context.getRequest().getRequestURI().matches("/payment/v(.*)/request")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNodeRQ = mapper.readTree(new InputStreamReader(context.getRequest().getInputStream(), StandardCharsets.UTF_8));
                if (jsonNodeRQ.has("paymentCode")) {
                    String paymentCode = jsonNodeRQ.get("paymentCode").textValue();
                    if (paymentCode.equals(POINT_CODE)) {
                        checkUserContact();
                    }
                }
            } catch (Exception exception) {
                log.info("NO REQUEST BODY OF /payment/v(.*)/request ENDPOINT");
            }
        } else if (context.getRequest().getRequestURI().matches("/skypoint/(.*)")) {
            checkUserContact();
        }
    }

    private void checkUserContact() {
        UserTokenModel userToken = jwtUtils.getUserToken();

        Optional<UserEntity> userEntity = userRP.findById(userToken.getUserId());

        if (userToken.getClientId().equals(environment.getProperty("spring.skybooking-app.front-end.client-id")) && userEntity.isPresent()) {
            UserEntity user = userEntity.get();

            if (user.getCode() == null || user.getCode().equals("")
                || user.getPhone() == null || user.getPhone().equals("")
                || user.getEmail() == null || user.getEmail().equals("")
                || user.getPassword() == null || user.getPassword().equals(""))
            {
                responseUnProcess();
            }
        }
    }
}
