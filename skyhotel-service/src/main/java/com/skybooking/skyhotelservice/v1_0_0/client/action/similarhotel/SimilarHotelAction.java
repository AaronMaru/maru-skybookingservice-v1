package com.skybooking.skyhotelservice.v1_0_0.client.action.similarhotel;

import com.skybooking.skyhotelservice.constant.EndpointConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.action.BaseAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.similarhotel.SimilarHotelDSRQ;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.StructureRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.similarhotel.SimilarRSDS;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

@Component
public class SimilarHotelAction extends BaseAction {

    public SimilarRSDS getSimilarHotel(SimilarHotelDSRQ request) {
        try {
            return client
                    .mutate()
                    .exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer -> {
                        clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                    }).build())
                    .build()
                    .post()
                    .uri(appConfig.getDISTRIBUTED_URL() + EndpointConstant.SimilarHotel.V1_0_0)
//                    .uri("http://127.0.0.1:9002/v1.0.0/similar-hotels")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(SimilarRSDS.class)
                    .block();
        } catch (Exception exception) {
            throw exception;
        }
    }

}
