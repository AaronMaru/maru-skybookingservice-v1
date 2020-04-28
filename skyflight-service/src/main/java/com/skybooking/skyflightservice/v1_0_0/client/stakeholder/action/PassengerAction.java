package com.skybooking.skyflightservice.v1_0_0.client.stakeholder.action;

import com.skybooking.skyflightservice.config.AppConfig;
import com.skybooking.skyflightservice.v1_0_0.client.stakeholder.ui.request.PassengerSRQ;
import com.skybooking.skyflightservice.v1_0_0.util.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PassengerAction {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private WebClient webClient;

    @Autowired
    private AuthUtility authUtility;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * create passenger
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param passengerSRQ
     * @return Mono
     */
    public Mono<Object> create(PassengerSRQ passengerSRQ) {

        return webClient
                .post()
                .uri(appConfig.getSTAKEHOLDER_URI() + appConfig.getSTAKEHOLDER_COMMON_VERSION() + "/passenger")
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
            .bodyValue(passengerSRQ)
                .retrieve()
                .bodyToMono(Object.class);
    }

}
