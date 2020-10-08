package com.skybooking.skyhotelservice.v1_0_0.service.payment;

import com.skybooking.skyhotelservice.constant.AttachmentConstant;
import com.skybooking.skyhotelservice.constant.BookingConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.action.event.PaymentAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.event.PaymentInfoRQEV;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.event.PaymentSuccessRQEV;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.attachment.AttachmentEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.HotelBookingEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.HotelPaymentTransactionEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.attachment.AttachmentRP;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.booking.HotelBookingRP;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.booking.HotelPaymentTransactionRP;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.service.history.HistoryBookingSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.payment.PaymentMailRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.payment.PaymentMandatoryRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail.BookingDetailRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.payment.PaymentMandatoryRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.payment.PaymentTransactionRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.payment.PriceInfoRS;
import com.skybooking.skyhotelservice.v1_0_0.util.GeneratorCM;
import com.skybooking.skyhotelservice.v1_0_0.util.aws.AwsPartCM;
import com.skybooking.skyhotelservice.v1_0_0.util.booking.BookingCM;
import com.skybooking.skyhotelservice.v1_0_0.util.calculator.NumberFormatter;
import com.skybooking.skyhotelservice.v1_0_0.util.datetime.DatetimeUtil;
import com.skybooking.skyhotelservice.v1_0_0.util.header.HeaderCM;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

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

    @Autowired
    private PaymentAction paymentAction;

    @Autowired
    private HeaderCM headerCM;

    @Autowired
    private AttachmentRP attachmentRP;

    @Autowired
    private AwsPartCM awsPartCM;

    @Autowired
    private HistoryBookingSV historyBookingSV;

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
        booking.setDiscountPaymentAmount(priceInfo.getDiscountPaymentMethodAmount());
        booking.setDiscountPaymentPercentage(priceInfo.getDiscountPaymentMethodPercentage());
        booking.setPaymentFeeAmount(priceInfo.getBasePaymentAmount());
        booking.setPaymentFeePercentage(priceInfo.getBasePaymentPercentage());
        bookingRP.save(booking);

        var paymentMandatory = new PaymentMandatoryRS();

        paymentMandatory.setAmount(priceInfo.getPaidAmount());
        paymentMandatory.setBookingCode(booking.getCode());
        paymentMandatory.setBookingId(booking.getId());
        paymentMandatory.setEmail(booking.getContactEmail());
        paymentMandatory.setPhoneNumber(booking.getContactPhone());
        paymentMandatory.setName(booking.getContactFullname());
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

        BigDecimal amount = NumberFormatter.trimAmount(booking.getTotalAmount());

        paymentMandatoryRS.setAmount(NumberFormatter.trimAmount(amount.subtract(booking.getDiscountPaymentAmount())));
        paymentMandatoryRS.setDescription("Hotel Description");
        paymentMandatoryRS.setEmail(booking.getContactEmail());
        paymentMandatoryRS.setPhoneNumber(booking.getContactPhone());
        paymentMandatoryRS.setName(booking.getContactFullname());
        paymentMandatoryRS.setSkyuserId(booking.getStakeholderUserId());
        paymentMandatoryRS.setCompanyId(booking.getStakeholderCompanyId());
        paymentMandatoryRS.setBookingId(booking.getId());
        paymentMandatoryRS.setBookingCode(booking.getCode());
        return paymentMandatoryRS;
    }

    @Override
    @Transactional
    public void updatePaymentSucceed(PaymentTransactionRQ paymentSucceedRQ) {
        BookingDetailRS bookingDetail = new BookingDetailRS();
        paymentSucceedRQ.setLang(headerCM.getLocalization());
        paymentSucceedRQ.setBookingDetail(bookingDetail);

        jmsTemplate.convertAndSend(SKY_HOTEL_PAYMENT, paymentSucceedRQ);
    }

    public HotelBookingEntity saveBookingPayment(PaymentTransactionRQ paymentSucceedRQ) {
        var booking = bookingRP.findByCode(paymentSucceedRQ.getBookingCode());

        var bookingPaymentTransaction = hotelPaymentTransactionRP.findByBookingId(booking.getId());

        if (bookingPaymentTransaction == null) {
            HotelPaymentTransactionEntity paymentTransactionEntity = new HotelPaymentTransactionEntity();

            paymentTransactionEntity.setBookingId(booking.getId());
            paymentTransactionEntity.setTransactionCode(generatorCM.transactionCode(hotelPaymentTransactionRP.getLatestRow()));
            paymentTransactionEntity.setAmount(paymentSucceedRQ.getAmount());
            paymentTransactionEntity.setCardHolderName(paymentSucceedRQ.getHolderName());
            paymentTransactionEntity.setCardNumber(paymentSucceedRQ.getCardNumber());
            paymentTransactionEntity.setCardType(paymentSucceedRQ.getCardType());
            paymentTransactionEntity.setBankName(paymentSucceedRQ.getBankName());
            paymentTransactionEntity.setDescription(paymentSucceedRQ.getDescription());
            paymentTransactionEntity.setPaymentMethod(paymentSucceedRQ.getMethod());
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

    public void sendMailPaymentSuccess(PaymentMailRQ paymentMailRQ) {

        var paymentSuccessRQEV = new PaymentSuccessRQEV();
        BeanUtils.copyProperties(paymentMailRQ, paymentSuccessRQEV);

        paymentAction.paymentSucces(paymentSuccessRQEV);

    }

    public void sendPaymentInfoBookingSuccess(PaymentMailRQ paymentMailRQ, BookingDetailRS bookingDetail) {

        var paymentInfoRQEV = new PaymentInfoRQEV();
        BeanUtils.copyProperties(paymentMailRQ, paymentInfoRQEV);

        if (paymentMailRQ.getType().equals("all")) {
            this.setReceiptData(bookingDetail);
            this.setVoucherData(bookingDetail);
        }

        paymentAction.paymentInfo(paymentInfoRQEV);

    }

    public void sendMailPaymentInfo(PaymentMailRQ paymentMailRQ) {

        BookingDetailRS bookingDetail = new BookingDetailRS();

        var paymentInfoRQEV = new PaymentInfoRQEV();
        BeanUtils.copyProperties(paymentMailRQ, paymentInfoRQEV);

        String type = paymentMailRQ.getType().equals("voucher") ? AttachmentConstant.SKYHOTEL_VOUCHER : AttachmentConstant.SKYHOTEL_RECEIPT;
        String local = headerCM.getLocalization();

        Optional<AttachmentEntity> attachInfo = attachmentRP.findByReferenceAndTypeAndLangCode(paymentMailRQ.getBookingCode(), type, local);

        if (attachInfo.isPresent()) {
            paymentInfoRQEV.setUrl(awsPartCM.partUrl(type, attachInfo.get().getPath() + "/" + attachInfo.get().getName()));
        } else {
            bookingDetail = historyBookingSV.detail(paymentMailRQ.getBookingCode(), "no-auth");
            if (type.equals("voucher")) {
                this.setVoucherData(bookingDetail);
            }

            if (type.equals("e-receipt")) {
                this.setReceiptData(bookingDetail);
            }
        }
        paymentAction.paymentInfo(paymentInfoRQEV);

    }

    public PaymentInfoRQEV setReceiptData (BookingDetailRS detail) {
        var paymentInfoRQEV = new PaymentInfoRQEV();

        paymentInfoRQEV.setPeriod(detail.getPeriod());
        paymentInfoRQEV.setAddress(detail.getHotelLocation());
        paymentInfoRQEV.setCustomerName(detail.getContactName());
        paymentInfoRQEV.setEmailAddress(detail.getEmailAddress());
        paymentInfoRQEV.setHotelName(detail.getHotelName());
        paymentInfoRQEV.setChargeDate(detail.getChargeDate() == null ? "" : DatetimeUtil.convertDateFormat(detail.getChargeDate()));

        if (detail.getRoom() != null) {
            paymentInfoRQEV.setRoomType(detail.getRoom().get(0).getType());
        }
        paymentInfoRQEV.setNumExtraBed(detail.getTotalExtraBed());
        paymentInfoRQEV.setNumRooms(detail.getTotalRoom());

        paymentInfoRQEV.setTotalExtraBedCharges(detail.getTotalExtraBedCharges());
        paymentInfoRQEV.setTotalRoomCharges(detail.getTotalRoomCharges());
        paymentInfoRQEV.setGrandTotal(detail.getTotalAmount());
        paymentInfoRQEV.setTotalPaymentFee(detail.getPaymentFeeAmount());

        return paymentInfoRQEV;
    }

    public PaymentInfoRQEV setVoucherData (BookingDetailRS detail) {
        var paymentInfoRQEV = new PaymentInfoRQEV();

        paymentInfoRQEV.setCustomerName(detail.getContactName());
        paymentInfoRQEV.setBookingReference(detail.getBookingReference());
        paymentInfoRQEV.setCountryResident(detail.getCountryResident());

        paymentInfoRQEV.setNumAdult(detail.getTotalAdult());
        paymentInfoRQEV.setNumChildren(detail.getTotalChildren());
        paymentInfoRQEV.setNumExtraBed(detail.getTotalExtraBed());
        paymentInfoRQEV.setNumPromotion(detail.getTotalPromotion());
        paymentInfoRQEV.setNumRooms(detail.getTotalRoom());

        if (detail.getRoom() != null) {
            paymentInfoRQEV.setRoomType(detail.getRoom().get(0).getType());
        }

        paymentInfoRQEV.setCardNo(detail.getCardNumber());
        paymentInfoRQEV.setPaymentMethod(detail.getPaymentMethod());
        paymentInfoRQEV.setExpired(detail.getExpired());

        paymentInfoRQEV.setArrival(detail.getCheckIn() == null ? "" : DatetimeUtil.convertDateFormat(detail.getCheckIn()));
        paymentInfoRQEV.setDeparture(detail.getCheckOut() == null ? "" : DatetimeUtil.convertDateFormat(detail.getCheckOut()));

        paymentInfoRQEV.setRemark(detail.getRemark());

        return paymentInfoRQEV;
    }


}
