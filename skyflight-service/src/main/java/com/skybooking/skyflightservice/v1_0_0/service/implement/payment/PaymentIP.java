package com.skybooking.skyflightservice.v1_0_0.service.implement.payment;

import com.skybooking.skyflightservice.constant.BookingConstant;
import com.skybooking.skyflightservice.constant.PaymentConstant;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingPaymentTransactionEntity;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingPaymentTransactionRP;
import com.skybooking.skyflightservice.v1_0_0.io.repository.booking.BookingRP;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.payment.PaymentSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment.PaymentMandatoryRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment.PaymentTransactionRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.payment.PaymentMandatoryRS;
import com.skybooking.skyflightservice.v1_0_0.util.GeneratorUtils;
import com.skybooking.skyflightservice.v1_0_0.util.activitylog.ActivityLoggingBean;
import com.skybooking.skyflightservice.v1_0_0.util.booking.BookingUtility;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.NumberFormatter;
import com.skybooking.skyflightservice.v1_0_0.util.classes.booking.PriceInfo;
import com.skybooking.skyflightservice.v1_0_0.util.notification.PushNotificationOptions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.skybooking.skyflightservice.config.ActiveMQConfig.SKY_FLIGHT_PAYMENT;

@Service
public class PaymentIP implements PaymentSV {

    @Autowired
    private BookingRP bookingRP;

    @Autowired
    private BookingPaymentTransactionRP bookingPaymentTransactionRP;

    @Autowired
    private BookingUtility bookingUtility;

    @Autowired
    private PushNotificationOptions pushNotification;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ActivityLoggingBean activityLog;

    @Override
    public PaymentMandatoryRS updateDiscountPaymentMethod(PaymentMandatoryRQ paymentMandatoryRQ) {

        var booking = bookingRP.getPnrCreated(
                paymentMandatoryRQ.getBookingCode(),
                BookingConstant.PNR_CREATED,
                PaymentConstant.PAYMENT_SELECTED,
                PaymentConstant.PAYMENT_CREATED,
                PaymentConstant.PAYMENT_PROCESSING
        );

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

        paymentMandatoryData.setAmount(priceInfo.getPaidAmount());
        paymentMandatoryData.setDescription("Flight Description");
        paymentMandatoryData.setName(bookingUpdated.getCustName());
        paymentMandatoryData.setPhoneNumber(bookingUpdated.getContPhone());
        paymentMandatoryData.setEmail(bookingUpdated.getContEmail());
        paymentMandatoryData.setBookingId(bookingUpdated.getId());
        paymentMandatoryData.setSkyuserId(booking.getStakeholderUserId());
        paymentMandatoryData.setCompanyId(booking.getStakeholderCompanyId());
        return paymentMandatoryData;
    }


    @Override
    public PaymentMandatoryRS getMandatoryData(String bookingCode) {

        PaymentMandatoryRS paymentMandatoryRS = new PaymentMandatoryRS();
        var booking = bookingRP.getPnrCreated(
                bookingCode,
                BookingConstant.PNR_CREATED,
                PaymentConstant.PAYMENT_SELECTED,
                PaymentConstant.PAYMENT_CREATED,
                PaymentConstant.PAYMENT_PROCESSING
        );

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
        paymentMandatoryRS.setSkyuserId(booking.getStakeholderUserId());
        paymentMandatoryRS.setCompanyId(booking.getStakeholderCompanyId());
        paymentMandatoryRS.setBookingId(booking.getId());
        paymentMandatoryRS.setBookingCode(booking.getBookingCode());
        paymentMandatoryRS.setPhoneCode(booking.getContPhonecode());

        return paymentMandatoryRS;
    }

    @Override
    public PaymentMandatoryRS getPointMandatoryData(String bookingCode) {
        return null;
    }


    @Override
    @Transactional
    public void updatePaymentSucceed(PaymentTransactionRQ paymentSucceedRQ) {

        jmsTemplate.convertAndSend(SKY_FLIGHT_PAYMENT, paymentSucceedRQ);

    }


    @Override
    public void paymentFail(PaymentTransactionRQ paymentTransactionRQ) {

        BookingEntity bookingEntity = bookingRP.findByBookingCode(paymentTransactionRQ.getBookingCode());

        BookingPaymentTransactionEntity bookingPaymentTransactionEntity = new BookingPaymentTransactionEntity();
        BeanUtils.copyProperties(paymentTransactionRQ, bookingPaymentTransactionEntity);

        bookingPaymentTransactionEntity.setTransactionId(GeneratorUtils.transactionCode(bookingPaymentTransactionRP.getLatestRow()));
        bookingPaymentTransactionEntity.setBookingId(bookingEntity.getId());

        bookingPaymentTransactionRP.save(bookingPaymentTransactionEntity);

        bookingEntity.setStatus(PaymentConstant.PAYMENT_FAIL);

        pushNotification.sendMessageToUsers("user_booked_flight_failed", bookingEntity.getId(), bookingEntity.getStakeholderUserId().longValue());

        bookingRP.save(bookingEntity);
    }

    @Override
    public BookingEntity saveBookingPayment(PaymentTransactionRQ paymentSucceedRQ) {

        var booking = bookingRP.getBookingByBookingCode(paymentSucceedRQ.getBookingCode());

        BookingPaymentTransactionEntity bookingPaymentTransaction =
                bookingPaymentTransactionRP.findByBookingIdAndStatus(booking.getId(), paymentSucceedRQ.getStatus());

        if (bookingPaymentTransaction == null) {

            BookingPaymentTransactionEntity bookingPaymentTransactionEntity = new BookingPaymentTransactionEntity();
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
            bookingPaymentTransactionEntity.setPipayStatus(paymentSucceedRQ.getPipayStatus());
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

            bookingPaymentTransactionRP.save(bookingPaymentTransactionEntity);

            booking.setStatus(PaymentConstant.PAYMENT_SUCCEED);
            bookingRP.save(booking);

            pushNotification.sendMessageToUsers("user_booked_flight_and_paid_success", booking.getId(), booking.getStakeholderUserId().longValue());

            var user = activityLog.getUser(booking.getStakeholderUserId());
            activityLog.activities(ActivityLoggingBean.Action.INDEX_TICKETING_PAYMENT, user, booking);
        }

        return booking;

    }


}
