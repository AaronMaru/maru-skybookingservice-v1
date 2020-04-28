package com.skybooking.skyflightservice.v1_0_0.client.skyhistory.action;

import com.skybooking.skyflightservice.config.AppConfig;
import com.skybooking.skyflightservice.v1_0_0.client.skyhistory.request.PaymentSucceedSendingRQ;
import com.skybooking.skyflightservice.v1_0_0.client.skyhistory.request.SendBookingPDFRQ;
import com.skybooking.skyflightservice.v1_0_0.util.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SkyhistoryAction {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private WebClient webClient;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send Payment mail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    public void sendPayment(SendBookingPDFRQ sendBookingPDFRQ) {

        webClient
                .post()
                .uri(appConfig.getSKYHISTORY_URI() + appConfig.getSKYHISTORY_VERSION() + "/payment-success/no-auth")
//                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
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

        webClient
                .post()
                .uri(appConfig.getSKYHISTORY_URI() + appConfig.getSKYHISTORY_VERSION() + "/receipt-itinerary")
//                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(sendBookingPDFRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();

    }
}
