package com.skybooking.skypointservice.v1_0_0.client.event.action;

import com.skybooking.skypointservice.config.AppConfig;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.NotificationRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.TopUpNotificationRQ;
import com.skybooking.skypointservice.v1_0_0.util.auth.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;

@Service
public class EventNotificationAction {
    @Autowired
    private WebClient webClient;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AuthUtility authUtility;

    public void topUpNotification(HttpServletRequest httpServletRequest, TopUpNotificationRQ topUpNotificationRQ) {

        webClient
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/notification/no-auth/top-up")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .bodyValue(topUpNotificationRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();
    }

    public void sendNotification(HttpServletRequest httpServletRequest, NotificationRQ notificationRQ) {

        webClient
                .post()
                .uri(appConfig.getEventUrl() + appConfig.getEventVersion() + "/notification")
                .header("X-localization", httpServletRequest.getHeader("X-localization"))
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(notificationRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();
    }

}
