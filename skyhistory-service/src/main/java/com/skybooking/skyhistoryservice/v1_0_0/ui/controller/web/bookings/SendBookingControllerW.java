package com.skybooking.skyhistoryservice.v1_0_0.ui.controller.web.bookings;

import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.booking.SendBookingSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.request.SendBookingPDFRQ;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "wv1.0.0")
public class SendBookingControllerW {

    @Autowired
    private SendBookingSV sendBookingSV;

    @Autowired
    private Localization localization;


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
