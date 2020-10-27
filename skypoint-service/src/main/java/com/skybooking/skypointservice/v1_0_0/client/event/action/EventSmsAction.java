package com.skybooking.skypointservice.v1_0_0.client.event.action;

import com.skybooking.skypointservice.config.AppConfig;
import com.skybooking.skypointservice.v1_0_0.client.ClientResponse;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.sms.*;
import com.skybooking.skypointservice.v1_0_0.util.auth.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;

@Service
public class EventSmsAction {
    @Autowired
    private WebClient webClient;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AuthUtility authUtility;

    public ClientResponse topUpSuccessSms(HttpServletRequest httpServletRequest, SkyPointTopUpSuccessSmsRQ skyPointTopUpSuccessSmsRQ) {

        return webClient.mutate()
                .build()
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/sms/no-auth/top-up/success")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .bodyValue(skyPointTopUpSuccessSmsRQ)
                .retrieve()
                .bodyToMono(ClientResponse.class)
                .block();

    }

    public void topUpFailedSms(HttpServletRequest httpServletRequest, SkyPointTopUpFailedSmsRQ skyPointTopUpFailedSmsRQ) {

        webClient
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/sms/no-auth/top-up/failed")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(skyPointTopUpFailedSmsRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();

    }


    public void earnedSms(HttpServletRequest httpServletRequest, SkyPointEarnSmsRQ skyPointEarnSmsRQ) {

        webClient
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/sms/earned")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(skyPointEarnSmsRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();

    }

    public void redeemedSms(HttpServletRequest httpServletRequest, SkyPointRedeemSmsRQ skyPointRedeemSmsRQ) {

        webClient
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/sms/redeem")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(skyPointRedeemSmsRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();

    }

    public void refundedSms(HttpServletRequest httpServletRequest, SkyPointRefundSmsRQ skyPointRefundSmsRQ) {

        webClient
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/sms/refund")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(skyPointRefundSmsRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();

    }
}
