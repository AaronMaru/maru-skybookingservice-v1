package com.skybooking.skyflightservice.v1_0_0.ui.controller;

import com.skybooking.skyflightservice.v1_0_0.service.interfaces.payment.PaymentSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment.PaymentMandatoryRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment.PaymentTransactionRQ;
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
     * Get mandatory data payment
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingCode
     * @return
     */
    @GetMapping("point/mandatory-data/{bookingCode}")
    public ResponseEntity getPaymentPointMandatory(@PathVariable String bookingCode) {
        return new ResponseEntity<>(paymentSV.getPointMandatoryData(bookingCode), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update payment succeed and going to issued air ticket
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentTransactionRQ
     * @return
     */
    @PostMapping("succeed")
    public void paymentSuccess(@RequestBody PaymentTransactionRQ paymentTransactionRQ) {
        System.out.println("halo world");
        paymentSV.updatePaymentSucceed(paymentTransactionRQ);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Fail Payment Save to DB
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentTransactionRQ
     * @return
     */
    @PostMapping("fail")
    public void paymentFail(@RequestBody PaymentTransactionRQ paymentTransactionRQ) {
        paymentSV.paymentFail(paymentTransactionRQ);
    }

}
