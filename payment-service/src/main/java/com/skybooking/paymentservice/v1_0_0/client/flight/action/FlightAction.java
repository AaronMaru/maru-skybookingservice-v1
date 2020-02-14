package com.skybooking.paymentservice.v1_0_0.client.flight.action;

import com.skybooking.paymentservice.config.AppConfig;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.request.FlightMandatoryDataRQ;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.request.FlightPaymentSucceedRQ;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.request.FlightTicketIssuedRQ;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.response.FlightMandatoryDataRS;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.response.FlightPaymentSucceedRS;
import com.skybooking.paymentservice.v1_0_0.client.flight.ui.response.FlightTicketIssuedRS;
import com.skybooking.paymentservice.v1_0_0.ui.model.response.airTicket.TicketIssuedRS;
import com.skybooking.paymentservice.v1_0_0.util.auth.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FlightAction {

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
    public FlightMandatoryDataRS updateDiscountPaymentMethod(FlightMandatoryDataRQ mandatoryDataRQ) {

        return client.mutate()
                .build()
                .post()
                .uri(appConfig.getFlightUrl() + appConfig.getFlightVersion() + "/payment/mandatory-data")
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(mandatoryDataRQ)
                .retrieve()
                .bodyToMono(FlightMandatoryDataRS.class)
                .block();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get mandatory data flight
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingCode
     * @return
     */
    public FlightMandatoryDataRS getMandatoryData(String bookingCode) {

        return client.mutate()
                .build()
                .get()
                .uri(appConfig.getFlightUrl() + appConfig.getFlightVersion() + "/payment/mandatory-data/" + bookingCode)
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
     * @param paymentSucceedRQ
     * @return
     */
    public FlightPaymentSucceedRS updateFlightPaymentSucceed(FlightPaymentSucceedRQ paymentSucceedRQ) {

        return client.mutate()
                .build()
                .post()
                .uri(appConfig.getFlightUrl() + appConfig.getFlightVersion() + "/payment/succeed")
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(paymentSucceedRQ)
                .retrieve()
                .bodyToMono(FlightPaymentSucceedRS.class)
                .block();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Issued air ticket
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param ticketIssuedRQ
     * @return
     */
    public TicketIssuedRS issuedAirTicket(FlightTicketIssuedRQ ticketIssuedRQ) {

        return client.mutate()
                .build()
                .post()
                .uri(appConfig.getFlightUrl() + appConfig.getFlightVersion() + "/ticketing/issued")
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .bodyValue(ticketIssuedRQ)
                .retrieve()
                .bodyToMono(TicketIssuedRS.class)
                .block();
    }
}
