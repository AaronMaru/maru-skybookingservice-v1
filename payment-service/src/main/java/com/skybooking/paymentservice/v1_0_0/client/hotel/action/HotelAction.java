package com.skybooking.paymentservice.v1_0_0.client.hotel.action;

import com.skybooking.paymentservice.config.AppConfig;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.request.PaymentSucceedRQ;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.response.FlightMandatoryDataRS;
import com.skybooking.paymentservice.v1_0_0.client.hotel.ui.request.HotelMandatoryDataRQ;
import com.skybooking.paymentservice.v1_0_0.client.hotel.ui.response.HotelMandatoryDataRS;
import com.skybooking.paymentservice.v1_0_0.util.auth.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class HotelAction {
    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AuthUtility authUtility;

    @Autowired
    private WebClient client;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update discount payment method in booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param mandatoryDataRQ
     * @return
     */
    public HotelMandatoryDataRS updateDiscountPaymentMethod(HotelMandatoryDataRQ mandatoryDataRQ) {
        return client.mutate()
                .build()
                .post()
                .uri(appConfig.getHotelUrl() + appConfig.getHotelVersion() + "/payment/mandatory-data")
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(mandatoryDataRQ)
                .retrieve()
                .bodyToMono(HotelMandatoryDataRS.class)
                .block();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get mandatory data hotel
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingCode
     * @return
     */
    public FlightMandatoryDataRS getMandatoryData(String bookingCode) {

        return client.mutate()
                .build()
                .get()
                .uri(appConfig.getHotelUrl() + appConfig.getHotelVersion() + "/payment/mandatory-data/" + bookingCode)
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .retrieve()
                .bodyToMono(FlightMandatoryDataRS.class)
                .block();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update booking flight is payment succeed
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param paymentSucceedRQ
     * @return
     */
    public void updateHotelPaymentSucceed(PaymentSucceedRQ paymentSucceedRQ) {

        client
                .post()
                .uri(appConfig.getHotelUrl()+ appConfig.getHotelVersion()+ "/payment/succeed")
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(paymentSucceedRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();
    }

}
