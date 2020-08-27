package com.skybooking.skyhotelservice.v1_0_0.client.action.hotel;

import com.skybooking.skyhotelservice.constant.EndpointConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.action.BaseAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.content.AvailabilityRQDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.content.HotelCodeRQDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.availability.AvailabilityRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.hotel.DestinationHotelCodeRSDS;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

@Service
public class AvailabilityAction extends BaseAction {

    public AvailabilityRSDS search(AvailabilityRQDS request) {

        try {
            return client
                .mutate()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                }).build())
                .build()
                .post()
                .uri(appConfig.getDISTRIBUTED_URL() + EndpointConstant.Availability.V1_0_0)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AvailabilityRSDS.class)
                .block();
        } catch (Exception exception) {
            throw exception;
        }

    }

    public DestinationHotelCodeRSDS fetchHotelCodes(HotelCodeRQDS request) {

        try {

            return client
                .mutate()
                .build()
                .post()
                .uri(appConfig.getDISTRIBUTED_URL() + EndpointConstant.DestinationHotelCode.V1_0_0)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(DestinationHotelCodeRSDS.class)
                .block();

        } catch (Exception exception) {
            throw exception;
        }
    }

}
