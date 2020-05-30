package com.skybooking.skyflightservice.v1_0_0.client.distributed.action;


import com.fasterxml.jackson.databind.JsonNode;
import com.skybooking.skyflightservice.config.AppConfig;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping.FlightShoppingRevalidateRQ;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping.RevalidateRQ;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.SabreBargainFinderRS;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.flight.FlightInfoTO;
import com.skybooking.skyflightservice.v1_0_0.security.token.DSTokenHolder;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.flight.FlightInfoSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightLegRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import com.skybooking.skyflightservice.v1_0_0.util.datetime.DatetimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingAction {

    @Autowired
    private DSTokenHolder dsTokenHolder;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private WebClient client;

    @Autowired
    private FlightInfoSV flightInfoSV;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get bargain finder Shopping
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param body
     * @return SabreBargainFinderRS
     */
    public Mono<SabreBargainFinderRS> getShopping(com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping.FlightShoppingRQ body) {

        return client
            .mutate()
            .exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer -> {
                clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
            }).build())
            .build()
            .post()
            .uri(appConfig.getDISTRIBUTED_URI() + "/flight/" + appConfig.getDISTRIBUTED_VERSION() + "/shopping-v3")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
            .bodyValue(body)
            .retrieve()
            .bodyToMono(SabreBargainFinderRS.class);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get bargain finder shopping list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param body
     * @return List
     */
    public List<SabreBargainFinderRS> getShoppingList(FlightShoppingRQ body) {

        var requests = new ArrayList<Mono<SabreBargainFinderRS>>();

        var airlines = flightInfoSV.getFlightInfoEnabled().stream().map(FlightInfoTO::getAirlineCode).collect(Collectors.toList());

        for (FlightLegRQ leg : body.getLegs()) {

            var request = new com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping.FlightShoppingRQ();
            request.setAirlines(airlines);

            var requestLeg = new com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping.FlightLegRQ();

            requestLeg.setDestination(leg.getArrival());
            requestLeg.setOrigin(leg.getDeparture());
            requestLeg.setDate(leg.getDate().toString());

            request.setAdult(body.getAdult());
            request.setChild(body.getChild());
            request.setInfant(body.getInfant());
            request.setClassType(body.getClassType());
            request.setTripType("one");
            request.getLegs().add(requestLeg);


            requests.add(this.getShopping(request));

        }

        return Mono.zip(requests, responses -> {

            var results = new ArrayList<SabreBargainFinderRS>();
            for (Object response : responses) {
                results.add((SabreBargainFinderRS) response);
            }

            return results;

        }).block();


    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Revalidate flight shopping for price and seats available
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return JsonNode
     */
    public SabreBargainFinderRS revalidateV2(RevalidateRQ request) {

        try {
            return client.mutate()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                }).build())
                .build()
                .post()
                .uri(appConfig.getDISTRIBUTED_URI() + "/flight/" + appConfig.getDISTRIBUTED_VERSION() + "/shopping/revalidate-v2")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(SabreBargainFinderRS.class).block();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Revalidate flight shopping for price and seats available
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return JsonNode
     */
    public JsonNode revalidate(RevalidateRQ request) {

        try {
            return client.mutate()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                }).build())
                .build()
                .post()
                .uri(appConfig.getDISTRIBUTED_URI() + "/flight/" + appConfig.getDISTRIBUTED_VERSION() + "/shopping/revalidate")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(JsonNode.class).block();
        } catch (Exception e) {
            throw e;
        }
    }

    public SabreBargainFinderRS getShoppingRevalidate(FlightShoppingRevalidateRQ body) {

        try {
            return client
                .mutate()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                }).build())
                .build()
                .post()
                .uri(appConfig.getDISTRIBUTED_URI() + "/flight/" + appConfig.getDISTRIBUTED_VERSION() + "/shopping-revalidate")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                .bodyValue(body)
                .retrieve()
                .bodyToMono(SabreBargainFinderRS.class).block();
        } catch (Exception e) {
            return null;
        }
    }

}
