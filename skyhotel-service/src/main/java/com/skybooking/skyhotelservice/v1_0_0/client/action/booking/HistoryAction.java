package com.skybooking.skyhotelservice.v1_0_0.client.action.booking;

import com.skybooking.skyhotelservice.constant.EndpointConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.action.BaseAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.content.HotelsRQDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.history.HistoryDSRQ;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.history.HistoryHBRS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.hotel.DataHotelDSRS;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class HistoryAction extends BaseAction {

    public HistoryHBRS listHistory(HistoryDSRQ request) {
        try {
            return client
                    .mutate()
                    .build()
                    .post()
//                    .uri("http://127.0.0.1:9003/v1.0.0/histories")
                    .uri(appConfig.getDISTRIBUTED_URL() + EndpointConstant.BookingHistory.V1_0_0)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(HistoryHBRS.class)
                    .block();
        } catch (Exception exception) {
            return null;
        }
    }

    public DataHotelDSRS historyContent(HotelsRQDS request) {
        try {
            return client
                    .mutate()
                    .build()
                    .post()
                    .uri(appConfig.getDISTRIBUTED_URL() + "/" + EndpointConstant.DataHotel.V1_0_0_BASIC)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(DataHotelDSRS.class)
                    .block();
        } catch (Exception exception) {
            return null;
        }
    }

}
