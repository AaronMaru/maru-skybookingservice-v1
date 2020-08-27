package com.skybooking.skyhotelservice.v1_0_0.client.action.recommend;

import com.skybooking.skyhotelservice.constant.EndpointConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.action.BaseAction;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.RecommendHotelRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class RecommendHotelAction extends BaseAction {

    public StructureRS listing(RecommendHotelRQ recommendHotelRQ) {

        return client
                .mutate()
                .build()
                .post()
                .uri(appConfig.getDISTRIBUTED_URL() + EndpointConstant.RecommendHotel.V1_0_0)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                .bodyValue(recommendHotelRQ)
                .retrieve()
                .bodyToMono(StructureRS.class)
                .block();

    }
}
