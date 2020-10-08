package com.skybooking.skyhotelservice.v1_0_0.client.action.hoteldata;

import com.skybooking.skyhotelservice.constant.EndpointConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.action.BaseAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.hoteldata.BasicHotelDataDSRQ;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.BaseRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.StructureRSDS;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

@Service
public class HotelDataAction extends BaseAction {

    public StructureRSDS getBasicHotel(BasicHotelDataDSRQ request) {

        try {
            return client
                .mutate()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                }).build())
                .build()
                .post()
                .uri(appConfig.getDISTRIBUTED_URL() + EndpointConstant.DataHotel.V1_0_0_BASIC)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(StructureRSDS.class)
                .block();
        } catch (Exception exception) {
            throw exception;
        }

    }

}
