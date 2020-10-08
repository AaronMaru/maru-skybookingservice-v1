package com.skybooking.paymentservice.v1_0_0.client.pipay.action;

import com.skybooking.paymentservice.config.AppConfig;
import com.skybooking.paymentservice.v1_0_0.client.pipay.model.request.PipayVerifyDataRQ;
import com.skybooking.paymentservice.v1_0_0.client.pipay.model.request.PipayVerifyRQ;
import com.skybooking.paymentservice.v1_0_0.client.pipay.model.response.PipayVerifyRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class PipayAction {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private WebClient client;

    public Boolean verify(Map<String, Object> request, BigDecimal amount) {

        try {
            PipayVerifyRQ pipayVerifyRQ = new PipayVerifyRQ(
                    new PipayVerifyDataRQ(request.get("orderID").toString(), request.get("processorID").toString())
            );

            PipayVerifyRS pipayVerifyRS = client.mutate()
                    .build()
                    .post()
                    .uri(appConfig.getPipayVerification())
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(pipayVerifyRQ)
                    .retrieve()
                    .bodyToMono(PipayVerifyRS.class)
                    .block();
            if (pipayVerifyRS.getResultCode().equals("0000") && (amount.compareTo(pipayVerifyRS.getData().getAmount()) == 0)) {
                return true;
            }

            return false;
        } catch (Exception e) {

            return false;
        }
    }
}
