package com.skybooking.skyhistoryservice.v1_0_0.ui.controller.web.bookings;

import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.SendBookingSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingNoAuthRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingPDFRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.PrintRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "wv1.0.0")
public class SendBookingControllerW {

    @Autowired
    private SendBookingSV sendBookingSV;

    @Autowired
    private LocalizationBean localization;
    
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail receipt
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResRS
     */
    @PostMapping("receipt")
    public ResRS receipt(@Valid @RequestBody SendBookingPDFRQ sendBookingPDFRQ) {
        sendBookingSV.sendBookingInfo(sendBookingPDFRQ, "receipt", "api_receipt_pdf");
        return localization.resAPI(HttpStatus.OK,"is_skb_info", "");
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail itinerary
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResRS
     */
    @PostMapping("itinerary")
    public ResRS itinerary(@Valid @RequestBody SendBookingPDFRQ sendBookingPDFRQ) {
        sendBookingSV.sendBookingInfo(sendBookingPDFRQ, "itinerary", "api_itinerary_pdf");
        return localization.resAPI(HttpStatus.OK,"is_skb_info", "");
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail receipt itinerary
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResRS
     */
    @PostMapping("receipt-itinerary")
    public ResRS receiptItinerary(@Valid @RequestBody SendBookingNoAuthRQ sendBookingNoAuthRQ) {
        sendBookingSV.sendBookingInfo(sendBookingNoAuthRQ);
        return localization.resAPI(HttpStatus.OK,"is_skb_info", "");
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail payment success
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResRS
     */
    @PostMapping("payment-success/no-auth")
    public ResRS paymentNoAuth(@Valid @RequestBody SendBookingNoAuthRQ sendBookingNoAuthRQ) {
        sendBookingSV.sendPaymentWithoutAuth(sendBookingNoAuthRQ);
        return localization.resAPI(HttpStatus.OK,"is_skb_info", "");
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send mail payment success
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResRS
     */
    @PostMapping("payment-success")
    public ResRS payment(@Valid @RequestBody SendBookingPDFRQ sendBookingPDFRQ) {
        sendBookingSV.sendPayment(sendBookingPDFRQ);
        return localization.resAPI(HttpStatus.OK,"is_skb_info", "");
    }

}
