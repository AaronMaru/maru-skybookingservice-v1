package com.skybooking.skypointservice.v1_0_0.client.event.action;

import com.skybooking.skypointservice.config.AppConfig;
import com.skybooking.skypointservice.v1_0_0.client.ClientResponse;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointTopUpSuccessRQ;
import com.skybooking.skypointservice.v1_0_0.util.auth.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;

@Service
public class EventDownloadAction {

    @Autowired
    private WebClient webClient;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AuthUtility authUtility;

    public ClientResponse downloadReceipt(HttpServletRequest httpServletRequest, SkyPointTopUpSuccessRQ skyPointTopUpSuccessRQ) {

        return webClient.mutate()
                .build()
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/sky-point/download/receipt")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(skyPointTopUpSuccessRQ)
                .retrieve()
                .bodyToMono(ClientResponse.class)
                .block();

    }

}
