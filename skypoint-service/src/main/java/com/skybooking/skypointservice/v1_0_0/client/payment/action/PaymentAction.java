package com.skybooking.skypointservice.v1_0_0.client.payment.action;

import com.skybooking.skypointservice.config.AppConfig;
import com.skybooking.skypointservice.v1_0_0.client.ClientResponse;
import com.skybooking.skypointservice.v1_0_0.client.payment.model.requset.PaymentGetUrlPaymentRQ;
import com.skybooking.skypointservice.v1_0_0.util.auth.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;

@Service
public class PaymentAction {

    @Autowired
    private WebClient webClient;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AuthUtility authUtility;

    public ClientResponse getPaymentMethodFee(String paymentMethodCode) {

        return webClient.mutate()
                .build()
                .get()
                .uri(appConfig.getPaymentUrl() + appConfig.getPaymentVersion() + "/methods/available/" + paymentMethodCode)
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .retrieve()
                .bodyToMono(ClientResponse.class)
                .block();
    }

    public ClientResponse getPaymentUrl(PaymentGetUrlPaymentRQ paymentGetUrlPaymentRQ) {

        return webClient.mutate()
                .build()
                .post()
                .uri(appConfig.getPaymentUrl() + appConfig.getPaymentNewVersion() + "/request")
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(paymentGetUrlPaymentRQ)
                .retrieve()
                .bodyToMono(ClientResponse.class)
                .block();
    }


}
