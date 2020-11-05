package com.skybooking.backofficeservice.v1_0_0.client.action.hotel;

import com.skybooking.backofficeservice.constant.EndpointConstant;
import com.skybooking.backofficeservice.v1_0_0.client.action.BaseAction;
import com.skybooking.backofficeservice.v1_0_0.client.model.response.hotel.HotelDetailServiceRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class HotelAction extends BaseAction {

    @Autowired
    private HttpServletRequest httpServletRequest;

    public HotelDetailServiceRS getHotelDetail(String hotelBookingCode)
    {
        try {
            return client
                .mutate()
                .build()
                .get()
                .uri(appConfig.getSKY_URL() + EndpointConstant.HotelBookingHistoryDetail.V1_0_0 + hotelBookingCode)
                .header(HttpHeaders.AUTHORIZATION, httpServletRequest.getHeader("Authorization"))
                .retrieve()
                .bodyToMono(HotelDetailServiceRS.class)
                .block();

        }catch (Exception exception){
            throw exception;
        }
    }
}
