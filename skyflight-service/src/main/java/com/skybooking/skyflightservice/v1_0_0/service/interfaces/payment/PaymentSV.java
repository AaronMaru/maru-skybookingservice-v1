package com.skybooking.skyflightservice.v1_0_0.service.interfaces.payment;

import com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment.PaymentMandatoryRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment.PaymentSucceedRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.payment.PaymentMandatoryRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.payment.PaymentSucceedRS;
import org.springframework.stereotype.Service;

@Service
public interface PaymentSV {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update discount payment method in booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentMandatoryRQ
     * @return
     */
    PaymentMandatoryRS updateDiscountPaymentMethod(PaymentMandatoryRQ paymentMandatoryRQ);



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get mandatory data from booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingCod
     * @return
     */
    PaymentMandatoryRS getMandatoryData(String bookingCod);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update payment succeed in bookings table
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentSucceedRQ
     * @return
     */
    PaymentSucceedRS updatePaymentSucceed(PaymentSucceedRQ paymentSucceedRQ);
}