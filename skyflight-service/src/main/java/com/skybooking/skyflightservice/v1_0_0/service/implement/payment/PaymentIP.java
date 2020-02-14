package com.skybooking.skyflightservice.v1_0_0.service.implement.payment;

import com.skybooking.skyflightservice.constant.BookingConstant;
import com.skybooking.skyflightservice.constant.PaymentConstant;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingPaymentTransactionEntity;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingPaymentTransactionRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.payment.PaymentSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment.PaymentMandatoryRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment.PaymentSucceedRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.payment.PaymentMandatoryRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.payment.PaymentSucceedRS;
import com.skybooking.skyflightservice.v1_0_0.util.GeneratorUtils;
import com.skybooking.skyflightservice.v1_0_0.util.booking.BookingUtility;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.NumberFormatter;
import com.skybooking.skyflightservice.v1_0_0.util.classes.booking.PriceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentIP implements PaymentSV {

    @Autowired
    private BookingRP bookingRP;

    @Autowired
    private BookingPaymentTransactionRP bookingPaymentTransactionRP;

    @Autowired
    private BookingUtility bookingUtility;



    @Override
    public PaymentMandatoryRS updateDiscountPaymentMethod(PaymentMandatoryRQ paymentMandatoryRQ) {

        var booking = bookingRP.getPnrCreated(paymentMandatoryRQ.getBookingCode(), BookingConstant.PNR_CREATED, PaymentConstant.PAYMENT_PROCESSING);
        PriceInfo priceInfo = bookingUtility.getPriceInfo(booking, paymentMandatoryRQ);

        /**
         * Update discount payment method & payment method fee in bookings table
         */
        booking.setDisPayMetAmount(priceInfo.getDiscountPaymentMethodAmount());
        booking.setDisPayMetPercentage(priceInfo.getDiscountPaymentMethodPercentage());
        booking.setPayMetFeePercentage(priceInfo.getPaymentMethodFeePercentage());
        booking.setPayMetFeeAmount(priceInfo.getPaymentMethodFeeAmount());

        var bookingUpdated = bookingRP.save(booking);
        var paymentMandatoryData = new PaymentMandatoryRS();

        paymentMandatoryData.setAmount(priceInfo.getFinalAmount());
        paymentMandatoryData.setDescription("Flight Description");
        paymentMandatoryData.setName(bookingUpdated.getCustName());
        paymentMandatoryData.setPhoneNumber(bookingUpdated.getContPhone());
        paymentMandatoryData.setEmail(bookingUpdated.getContEmail());

        return paymentMandatoryData;
    }



    @Override
    public PaymentMandatoryRS getMandatoryData(String bookingCod) {

        PaymentMandatoryRS paymentMandatoryRS = new PaymentMandatoryRS();
        var booking = bookingRP.getPnrCreated(bookingCod, BookingConstant.PNR_CREATED, PaymentConstant.PAYMENT_PROCESSING);

        /**
         * Update booking status to On progressing in bookings table
         */
        booking.setStatus(PaymentConstant.PAYMENT_PROCESSING);
        bookingRP.save(booking);

        var amount = NumberFormatter.trimAmount(booking.getTotalAmount().add(booking.getMarkupAmount()).add(booking.getMarkupPayAmount()));

        paymentMandatoryRS.setAmount(NumberFormatter.trimAmount(amount.subtract(booking.getDisPayMetAmount())));
        paymentMandatoryRS.setDescription("Flight Description");
        paymentMandatoryRS.setName(booking.getCustName());
        paymentMandatoryRS.setPhoneNumber(booking.getContPhone());
        paymentMandatoryRS.setEmail(booking.getContEmail());

        return paymentMandatoryRS;
    }



    @Override
    public PaymentSucceedRS updatePaymentSucceed(PaymentSucceedRQ paymentSucceedRQ) {

        BookingPaymentTransactionEntity bookingPaymentTransactionEntity = new BookingPaymentTransactionEntity();
        var booking = bookingRP.getBookingByBookingCode(paymentSucceedRQ.getBookingCode());

        bookingPaymentTransactionEntity.setBookingId(booking.getId());
        bookingPaymentTransactionEntity.setTransactionId(GeneratorUtils.transactionCode(bookingPaymentTransactionRP.getLatestRow()));
        bookingPaymentTransactionEntity.setAmount(paymentSucceedRQ.getAmount());
        bookingPaymentTransactionEntity.setHolderName(paymentSucceedRQ.getHolderName());
        bookingPaymentTransactionEntity.setCardNumber(paymentSucceedRQ.getCardNumber());
        bookingPaymentTransactionEntity.setCvv(paymentSucceedRQ.getCvv());
        bookingPaymentTransactionEntity.setCardType(paymentSucceedRQ.getCardType());
        bookingPaymentTransactionEntity.setBankName(paymentSucceedRQ.getBankName());
        bookingPaymentTransactionEntity.setDescription(paymentSucceedRQ.getDescription());
        bookingPaymentTransactionEntity.setMethod(paymentSucceedRQ.getMethod());
        bookingPaymentTransactionEntity.setAmount(paymentSucceedRQ.getAmount());
        bookingPaymentTransactionEntity.setStatus(paymentSucceedRQ.getStatus());
        bookingPaymentTransactionEntity.setPipayStatus(paymentSucceedRQ.getPipiyStatus());
        bookingPaymentTransactionEntity.setTransId(paymentSucceedRQ.getTransId());
        bookingPaymentTransactionEntity.setOrderId(paymentSucceedRQ.getOrderId());
        bookingPaymentTransactionEntity.setProcessorId(paymentSucceedRQ.getProcessorId());
        bookingPaymentTransactionEntity.setDigest(paymentSucceedRQ.getDigest());
        bookingPaymentTransactionEntity.setPaymentCode(paymentSucceedRQ.getPaymentCode());
        bookingPaymentTransactionEntity.setCurrency(paymentSucceedRQ.getCurrency());
        bookingPaymentTransactionEntity.setIpay88Status(paymentSucceedRQ.getIpay88Status());
        bookingPaymentTransactionEntity.setAuthCode(paymentSucceedRQ.getAuthCode());
        bookingPaymentTransactionEntity.setSignature(paymentSucceedRQ.getSignature());
        bookingPaymentTransactionEntity.setIpay88PaymentId(paymentSucceedRQ.getIpay88PaymentId());

        var created = bookingPaymentTransactionRP.save(bookingPaymentTransactionEntity);
        PaymentSucceedRS paymentSucceedRS = new PaymentSucceedRS();

        paymentSucceedRS.setAmount(created.getAmount());
        paymentSucceedRS.setBookingId(created.getBookingId());
        paymentSucceedRS.setTransactionId(created.getTransactionId());
        paymentSucceedRS.setSlug(created.getSlug());
        paymentSucceedRS.setBookingCode(paymentSucceedRQ.getBookingCode());
        paymentSucceedRS.setHolderName(created.getHolderName());
        paymentSucceedRS.setCardNumber(created.getCardNumber());
        paymentSucceedRS.setCvv(created.getCvv());
        paymentSucceedRS.setCardType(created.getCardType());
        paymentSucceedRS.setBankName(created.getBankName());
        paymentSucceedRS.setDescription(created.getDescription());
        paymentSucceedRS.setStatus(created.getStatus());
        paymentSucceedRS.setPipiyStatus(created.getPipayStatus());
        paymentSucceedRS.setTransId(created.getTransId());
        paymentSucceedRS.setOrderId(created.getOrderId());
        paymentSucceedRS.setProcessorId(created.getProcessorId());
        paymentSucceedRS.setDigest(created.getDigest());
        paymentSucceedRS.setMethod(created.getMethod());
        paymentSucceedRS.setAmount(created.getAmount());
        paymentSucceedRS.setCurrency(created.getCurrency());
        paymentSucceedRS.setIpay88Status(created.getPipayStatus());
        paymentSucceedRS.setAuthCode(created.getAuthCode());
        paymentSucceedRS.setSignature(created.getSignature());
        paymentSucceedRS.setIpay88PaymentId(created.getIpay88PaymentId());
        paymentSucceedRS.setPaymentCode(created.getPaymentCode());

        booking.setStatus(PaymentConstant.PAYMENT_SUCCEED);
        bookingRP.save(booking);

        return paymentSucceedRS;
    }
}
