package com.skybooking.skyhotelservice.v1_0_0.client.action.booking;

import com.skybooking.skyhotelservice.constant.EndpointConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.action.BaseAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking.BookingConfirmationHBRQ;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking.CheckRateHBRQ;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking.ReserveDSRQ;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.StructureRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking.BookingConfirmationHBRS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking.CheckRateHBRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BookingAction extends BaseAction {

    public Mono<CheckRateHBRS> checkRate(CheckRateHBRQ request) {
        try {
            return client
                    .mutate()
                    .build()
                    .post()
                    .uri(appConfig.getDISTRIBUTED_URL() + EndpointConstant.CheckRate.V1_0_0)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(CheckRateHBRS.class);
        } catch (Exception exception) {

            return null;

        }

    }

    public Mono<StructureRSDS> reserve(ReserveDSRQ request) {
        try {
            return client
                .mutate()
                .build()
                .post()
                .uri(appConfig.getDISTRIBUTED_URL() + EndpointConstant.Reserve.V1_0_0)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(StructureRSDS.class);
        } catch (Exception exception) {

            return null;

        }

    }

    public Mono<BookingConfirmationHBRS> confirmBooking(BookingConfirmationHBRQ request) {
        return client
            .mutate()
            .build()
            .post()
            .uri(appConfig.getDISTRIBUTED_URL() + EndpointConstant.Booking.V1_0_0)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
            .bodyValue(request)
            .retrieve()
            .bodyToMono(BookingConfirmationHBRS.class);
    }

}
