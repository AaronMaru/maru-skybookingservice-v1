package com.skybooking.skyhotelservice.v1_0_0.ui.controller.payment;

import com.skybooking.skyhotelservice.v1_0_0.service.payment.PaymentSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.payment.PaymentMailRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.payment.PaymentMandatoryRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.payment.PaymentTransactionRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0/payment")
public class PaymentController extends BaseController {

    @Autowired
    private PaymentSV paymentSV;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Payment method
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param paymentMandatoryRQ
     * @Return
     */
    @PostMapping("mandatory-data")
    public ResponseEntity paymentMandatory(@Valid @RequestBody PaymentMandatoryRQ paymentMandatoryRQ) {
        return new ResponseEntity<>(paymentSV.payment(paymentMandatoryRQ), HttpStatus.OK);
    }

    @GetMapping("mandatory-data/{bookingCode}")
    public ResponseEntity getPaymentMandatory(@PathVariable String bookingCode) {
        return new ResponseEntity<>(paymentSV.getMandatoryData(bookingCode), HttpStatus.OK);
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
        paymentSV.updatePaymentSucceed(paymentTransactionRQ);
    }

    @PostMapping("payment-success")
    public void mailPaymentSuccess(@RequestBody PaymentMailRQ paymentMailRQ) {
        paymentSV.sendMailPaymentSuccess(paymentMailRQ);
    }

    @PostMapping("payment-info")
    public ResponseEntity<StructureRS> mailPaymentInfo(@RequestBody PaymentMailRQ paymentMailRQ) {
        return response(paymentSV.sendMailPaymentInfo(paymentMailRQ));
    }

}
