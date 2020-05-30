package com.skybooking.skyflightservice.v1_0_0.client.skyhistory.action;

import com.skybooking.skyflightservice.config.AppConfig;
import com.skybooking.skyflightservice.v1_0_0.client.skyhistory.request.SendBookingPDFRQ;
import com.skybooking.skyflightservice.v1_0_0.io.repository.redis.BookingLanguageRedisRP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SkyhistoryAction {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private WebClient webClient;

    @Autowired
    private BookingLanguageRedisRP bookingLanguageRedisRP;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send Payment mail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    public void sendPayment(SendBookingPDFRQ sendBookingPDFRQ) {

        var bookingLanguageCached = bookingLanguageRedisRP.findById(sendBookingPDFRQ.getBookingCode());

        webClient
                .post()
                .uri(appConfig.getSKYHISTORY_URI() + appConfig.getSKYHISTORY_VERSION() + "/payment-success/no-auth")
                .header("X-localization", bookingLanguageCached.get().getLanguage())
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

        var bookingLanguageCached = bookingLanguageRedisRP.findById(sendBookingPDFRQ.getBookingCode());

        webClient
                .post()
                .uri(appConfig.getSKYHISTORY_URI() + appConfig.getSKYHISTORY_VERSION() + "/receipt-itinerary")
                .header("X-localization", bookingLanguageCached.get().getLanguage())
                .bodyValue(sendBookingPDFRQ)
                .retrieve()
                .bodyToMono(Object.class)
                .subscribe();

    }
}
