package com.skybooking.skyflightservice.v1_0_0.service.interfaces.payment;

import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment.PaymentMandatoryRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment.PaymentTransactionRQ;
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
     * Get mandatory data from booking
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param bookingCode
     * @return
     */
    PaymentMandatoryRS getPointMandatoryData(String bookingCode);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update payment succeed in bookings table
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentSucceedRQ
     * @return
     */
    void updatePaymentSucceed(PaymentTransactionRQ paymentSucceedRQ);

    void paymentFail(PaymentTransactionRQ paymentTransactionRQ);

    BookingEntity saveBookingPayment(PaymentTransactionRQ paymentSucceedRQ);
}
