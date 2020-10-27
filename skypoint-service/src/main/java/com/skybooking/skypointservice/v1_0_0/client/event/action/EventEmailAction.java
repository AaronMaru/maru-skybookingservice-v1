package com.skybooking.skypointservice.v1_0_0.client.event.action;

import com.skybooking.skypointservice.config.AppConfig;
import com.skybooking.skypointservice.v1_0_0.client.ClientResponse;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointMailRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointTopUpFailedRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointTopUpSuccessRQ;
import com.skybooking.skypointservice.v1_0_0.util.auth.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;

@Service
public class EventEmailAction {
    @Autowired
    private WebClient webClient;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AuthUtility authUtility;

    public ClientResponse topUpEmail(HttpServletRequest httpServletRequest, SkyPointTopUpSuccessRQ skyPointTopUpSuccessRQ) {

        return webClient.mutate()
                .build()
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/email/no-auth/top-up/success")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .bodyValue(skyPointTopUpSuccessRQ)
                .retrieve()
                .bodyToMono(ClientResponse.class)
                .block();

    }

    public void topUpFailedMail(HttpServletRequest httpServletRequest, SkyPointTopUpFailedRQ skyPointTopUpFailedRQ) {

        webClient
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/email/no-auth/top-up/failed")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(skyPointTopUpFailedRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();

    }


    public void earnedEmail(HttpServletRequest httpServletRequest, SkyPointMailRQ skyPointMailRQ) {

        webClient
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/email/earned")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(skyPointMailRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();

    }

    public void redeemEmail(HttpServletRequest httpServletRequest, SkyPointMailRQ skyPointMailRQ) {

        webClient
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/email/redeem")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(skyPointMailRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();

    }

    public void refundedEmail(HttpServletRequest httpServletRequest, SkyPointMailRQ skyPointMailRQ) {

        webClient
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/email/refund")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(skyPointMailRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();

    }

}
