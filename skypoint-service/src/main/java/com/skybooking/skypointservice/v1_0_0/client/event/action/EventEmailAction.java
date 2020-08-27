package com.skybooking.skypointservice.v1_0_0.client.event.action;

import com.skybooking.skypointservice.config.AppConfig;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointEarnedRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointRedeemRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.util.auth.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;

@Service
public class EventEmailAction {
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private WebClient webClient;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AuthUtility authUtility;

    public void topUpEmail(SkyPointTopUpRQ skyPointTopUpRQ) {

        webClient
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/email/no-auth/top-up")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .bodyValue(skyPointTopUpRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();

    }

    public void earnedEmail(SkyPointEarnedRQ skyPointEarnedRQ) {

        webClient
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/email/earned")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(skyPointEarnedRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();

    }

    public void redeemEmail(SkyPointRedeemRQ skyPointRedeemRQ) {

        webClient
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/email/redeem")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(skyPointRedeemRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();

    }

}
