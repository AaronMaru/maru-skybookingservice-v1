package com.skybooking.skyflightservice.v1_0_0.ui.controller;

import com.skybooking.skyflightservice.v1_0_0.service.interfaces.payment.PaymentSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment.PaymentMandatoryRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment.PaymentSucceedRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.payment.PaymentSucceedRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * ---------------------------------------------------------------------------------------------------------------------
 * INTERNAL PROCESS AND COMMUNICATION BETWEEN PAYMENT SERVICE WITH FLIGHT SERVICE
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>
 * 1. Update data booking
 * 2. Get data booking
 */
@RestController
@RequestMapping("v1.0.0/payment")
public class PaymentController {

    @Autowired
    private PaymentSV paymentSV;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update discount payment method
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentMandatoryRQ
     * @return
     */
    @PostMapping("mandatory-data")
    public ResponseEntity updateDiscountPaymentMethod(@RequestBody PaymentMandatoryRQ paymentMandatoryRQ) {
        return new ResponseEntity<>(paymentSV.updateDiscountPaymentMethod(paymentMandatoryRQ), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get mandatory data payment
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingCode
     * @return
     */
    @GetMapping("mandatory-data/{bookingCode}")
    public ResponseEntity getPaymentMandatory(@PathVariable String bookingCode) {
        return new ResponseEntity<>(paymentSV.getMandatoryData(bookingCode), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update payment succeed and going to issued air ticket
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentSucceedRQ
     * @return
     */
    @PostMapping("succeed")
    public ResponseEntity paymentSuccess(@RequestBody PaymentSucceedRQ paymentSucceedRQ) {

        PaymentSucceedRS paymentSucceedRS = paymentSV.updatePaymentSucceed(paymentSucceedRQ);
        if (paymentSucceedRS.getBookingCode().isBlank()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(paymentSucceedRS, HttpStatus.OK);
    }
}
