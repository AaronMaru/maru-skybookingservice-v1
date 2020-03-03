package com.skybooking.paymentservice.v1_0_0.client.stakeholder.action;

import com.skybooking.paymentservice.config.AppConfig;
import com.skybooking.paymentservice.v1_0_0.client.stakeholder.ui.request.SendBookingPDFRQ;
import com.skybooking.paymentservice.v1_0_0.util.auth.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SkyHistoryAction {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AuthUtility authUtility;

    @Autowired
    private WebClient client;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send Payment mail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    public void sendPayment(SendBookingPDFRQ sendBookingPDFRQ) {

        client
                .post()
                .uri(appConfig.getStakeholder() + appConfig.getStakeholderVersion() + "/payment-success/no-auth")
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(sendBookingPDFRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send Payment mail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    public void sendReceiptWithItinerary(SendBookingPDFRQ sendBookingPDFRQ) {

        client
                .post()
                .uri(appConfig.getStakeholder() + appConfig.getStakeholderVersion() + "/receipt-itinerary")
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(sendBookingPDFRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();

    }
}
