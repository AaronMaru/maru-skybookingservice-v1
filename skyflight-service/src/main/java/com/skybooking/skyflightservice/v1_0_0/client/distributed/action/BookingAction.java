package com.skybooking.skyflightservice.v1_0_0.client.distributed.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.skybooking.skyflightservice.config.AppConfig;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking.BookingCreateDRQ;
import com.skybooking.skyflightservice.v1_0_0.security.token.DSTokenHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BookingAction {

    @Autowired
    private DSTokenHolder dsTokenHolder;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private WebClient client;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Request Create Passenger Name Records to Supplier
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return DBookingRS
     */
    public JsonNode create(BookingCreateDRQ request) {

        return client.mutate()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                }).build())
                .build()
                .post()
                .uri(appConfig.getDISTRIBUTED_URI() + "/flight/" + appConfig.getDISTRIBUTED_VERSION() + "/booking/create")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
            .bodyValue(request)
                .retrieve()
            .bodyToMono(JsonNode.class).block();
    }
}
