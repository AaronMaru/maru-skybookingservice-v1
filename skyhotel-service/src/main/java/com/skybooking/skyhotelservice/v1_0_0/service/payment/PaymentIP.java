package com.skybooking.skyhotelservice.v1_0_0.service.payment;

import com.skybooking.skyhotelservice.constant.BookingConstant;
import com.skybooking.skyhotelservice.constant.PaymentTypeConstant;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.HotelBookingEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.HotelPaymentRuleEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.HotelPaymentTransactionEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.booking.HotelBookingRP;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.booking.HotelPaymentRuleRP;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.booking.HotelPaymentTransactionRP;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.payment.PaymentMandatoryRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.payment.PaymentMandatoryRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.payment.PaymentTransactionRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.payment.PriceInfoRS;
import com.skybooking.skyhotelservice.v1_0_0.util.GeneratorCM;
import com.skybooking.skyhotelservice.v1_0_0.util.booking.BookingCM;
import com.skybooking.skyhotelservice.v1_0_0.util.calculator.CalculatorCM;
import com.skybooking.skyhotelservice.v1_0_0.util.calculator.NumberFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.skybooking.skyhotelservice.config.ActiveMQConfig.SKY_HOTEL_PAYMENT;

@Service
public class PaymentIP extends BaseServiceIP implements PaymentSV {

    @Autowired
    private HotelBookingRP bookingRP;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private BookingCM bookingCM;

    @Autowired
    private HotelPaymentTransactionRP hotelPaymentTransactionRP;

    @Autowired
    private GeneratorCM generatorCM;

    public PaymentMandatoryRS payment(PaymentMandatoryRQ paymentRQ) {

        var booking = bookingRP.getPnrCreated(paymentRQ.getBookingCode(),
                BookingConstant.PENDING,
                BookingConstant.PAYMENT_SELECTED,
                BookingConstant.PAYMENT_CREATED,
                BookingConstant.PAYMENT_PROCESSING
        );

        /**
         * Update discount payment method & payment method fee in bookings table
         */
        PriceInfoRS priceInfo = bookingCM.getPriceInfo(booking, paymentRQ);
        booking.setDisPaymentAmount(priceInfo.getDiscountPaymentMethodAmount());
        booking.setDisPaymentPercentage(priceInfo.getDiscountPaymentMethodPercentage());
        booking.setBasePaymentAmount(priceInfo.getBasePaymentAmount());
        booking.setBasePaymentPercentage(priceInfo.getBasePaymentPercentage());
        bookingRP.save(booking);

        var paymentMandatory = new PaymentMandatoryRS();

        paymentMandatory.setAmount(priceInfo.getPaidAmount());
        paymentMandatory.setBookingCode(booking.getCode());
        paymentMandatory.setBookingId(booking.getId());
        paymentMandatory.setEmail(booking.getContEmail());
        paymentMandatory.setPhoneNumber(booking.getContPhone());
        paymentMandatory.setName(booking.getCustName());
        paymentMandatory.setDescription("Hotel Description");

        return paymentMandatory;

    }

    public PaymentMandatoryRS getMandatoryData(String bookingCode) {

        PaymentMandatoryRS paymentMandatoryRS = new PaymentMandatoryRS();

        var booking = bookingRP.getPnrCreated(bookingCode,
                BookingConstant.PENDING,
                BookingConstant.PAYMENT_SELECTED,
                BookingConstant.PAYMENT_CREATED,
                BookingConstant.PAYMENT_PROCESSING
        );

        booking.setStatus(BookingConstant.PAYMENT_PROCESSING);
        bookingRP.save(booking);

        BigDecimal amount = NumberFormatter.trimAmount(booking.getCost().add(booking.getMarkupAmount()).add(booking.getPaymentFeeAmount()));

        paymentMandatoryRS.setAmount(NumberFormatter.trimAmount(amount.subtract(booking.getDisPaymentAmount())));
        System.out.println(paymentMandatoryRS.getAmount());
        paymentMandatoryRS.setDescription("Hotel Description");
        paymentMandatoryRS.setEmail(booking.getContEmail());
        paymentMandatoryRS.setPhoneNumber(booking.getContPhone());
        paymentMandatoryRS.setName(booking.getCustName());
        paymentMandatoryRS.setSkyuserId(booking.getStakeholderUserId());
        paymentMandatoryRS.setCompanyId(booking.getStakeholderCompanyId());
        paymentMandatoryRS.setBookingId(booking.getId());
        paymentMandatoryRS.setBookingCode(booking.getCode());
        return paymentMandatoryRS;
    }

    @Override
    @Transactional
    public void updatePaymentSucceed(PaymentTransactionRQ paymentSucceedRQ) {
        jmsTemplate.convertAndSend(SKY_HOTEL_PAYMENT, paymentSucceedRQ);
    }


    public HotelBookingEntity saveBookingPayment(PaymentTransactionRQ paymentSucceedRQ) {
        var booking = bookingRP.findByCode(paymentSucceedRQ.getBookingCode());
        var bookingPaymentTransaction = hotelPaymentTransactionRP.findByBookingIdAndStatus(booking.getId(), paymentSucceedRQ.getStatus());

        if (bookingPaymentTransaction == null) {
            HotelPaymentTransactionEntity paymentTransactionEntity = new HotelPaymentTransactionEntity();

            paymentTransactionEntity.setBookingId(booking.getId());
            paymentTransactionEntity.setTransactionId(generatorCM.transactionCode(hotelPaymentTransactionRP.getLatestRow()));
            paymentTransactionEntity.setAmount(paymentSucceedRQ.getAmount());
            paymentTransactionEntity.setCardHolderName(paymentSucceedRQ.getHolderName());
            paymentTransactionEntity.setCardNumber(paymentSucceedRQ.getCardNumber());
            paymentTransactionEntity.setCardType(paymentSucceedRQ.getCardType());
            paymentTransactionEntity.setBankName(paymentSucceedRQ.getBankName());
            paymentTransactionEntity.setDescription(paymentSucceedRQ.getDescription());
            paymentTransactionEntity.setPaymentMethod(paymentSucceedRQ.getMethod());
            paymentTransactionEntity.setAmount(paymentSucceedRQ.getAmount());
            paymentTransactionEntity.setStatus(paymentSucceedRQ.getStatus());
            paymentTransactionEntity.setPipayStatus(paymentSucceedRQ.getPipayStatus());
            paymentTransactionEntity.setTransactionId(paymentSucceedRQ.getTransId());
            paymentTransactionEntity.setOrderId(paymentSucceedRQ.getOrderId());
            paymentTransactionEntity.setProcessorId(paymentSucceedRQ.getProcessorId());
            paymentTransactionEntity.setDigest(paymentSucceedRQ.getDigest());
            paymentTransactionEntity.setPaymentCode(paymentSucceedRQ.getPaymentCode());
            paymentTransactionEntity.setCurrencyCode(paymentSucceedRQ.getCurrency());
            paymentTransactionEntity.setIpay88Status(paymentSucceedRQ.getIpay88Status());
            paymentTransactionEntity.setAuthCode(paymentSucceedRQ.getAuthCode());
            paymentTransactionEntity.setSignature(paymentSucceedRQ.getSignature());
            paymentTransactionEntity.setIpay88PaymentId(paymentSucceedRQ.getIpay88PaymentId());

            hotelPaymentTransactionRP.save(paymentTransactionEntity);

            booking.setStatus(BookingConstant.PAYMENT_SUCCEED);
            bookingRP.save(booking);
        }

        return booking;

    }

}
