package com.skybooking.eventservice.v1_0_0.ui.controller.hotel;

import com.skybooking.eventservice.v1_0_0.service.email.HotelEmailSV;
import com.skybooking.eventservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.eventservice.v1_0_0.ui.model.request.hotel.PaymentInfoRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.request.hotel.PaymentSuccessRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0/email")
public class HotelEmailController extends BaseController {

    @Autowired
    private HotelEmailSV hotelEmailSV;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send payment success to user email after payment
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param sendRQ
     * @Return String
     * -----------------------------------------------------------------------------------------------------------------
     */
    @PostMapping("/payment-success")
    public ResponseEntity<StructureRS> sendPaymentSuccess(@Valid @RequestBody PaymentSuccessRQ sendRQ) {
        hotelEmailSV.paymentSuccess(sendRQ);
        return response(new StructureRS("Sending successfully"));
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Send payment info to user email after confirm booking from hotelbed
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param sendRQ
     * @Return String
     * -----------------------------------------------------------------------------------------------------------------
     */
    @PostMapping("/payment-info")
    public ResponseEntity<StructureRS> sendPaymentInfo(@Valid @RequestBody PaymentInfoRQ paymentInfoRQ) {
        hotelEmailSV.paymentInfo(paymentInfoRQ);
        return response(new StructureRS("Sending successfully"));
    }

}
