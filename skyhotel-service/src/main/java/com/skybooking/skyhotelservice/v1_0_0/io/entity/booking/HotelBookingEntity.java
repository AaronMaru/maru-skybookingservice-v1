package com.skybooking.skyhotelservice.v1_0_0.io.entity.booking;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "hotel_booking")
public class HotelBookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stakeholder_user_id")
    private Long stakeholderUserId;

    @Column(name = "stakeholder_company_id")
    private Long stakeholderCompanyId;

    private String code;

    // Reference from DS
    @Column(name = "booking_reference")
    private String bookingReference;

    // Cancellation Reference
    @Column(name = "reference_code")
    private String referenceCode;

    @Column(name = "check_in")
    private Date checkIn;

    @Column(name = "check_out")
    private Date checkOut;

    @Column(name = "markup_amount")
    private BigDecimal markupAmount = BigDecimal.ZERO;

    @Column(name = "payment_fee_amount")
    private BigDecimal paymentFeeAmount = BigDecimal.ZERO;

    @Column(name = "payment_fee_percentage")
    private BigDecimal paymentFeePercentage = BigDecimal.ZERO;

    @Column(name = "markup_pay_percentage")
    private BigDecimal markupPayPercentage = BigDecimal.ZERO;

    @Column(name = "markup_pay_amount")
    private BigDecimal markupPayAmount = BigDecimal.ZERO;

    @Column(name = "discount_payment_percentage")
    private BigDecimal discountPaymentPercentage = BigDecimal.ZERO;

    @Column(name = "discount_payment_amount")
    private BigDecimal discountPaymentAmount = BigDecimal.ZERO;

    @Column(name = "total_amount_before_discount")
    private BigDecimal totalAmountBeforeDiscount = BigDecimal.ZERO;

    private BigDecimal cost = BigDecimal.ZERO;

    @Column(name = "total_amount")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "refund_fee_percentage")
    private BigDecimal refundFeePercentage = BigDecimal.ZERO;

    @Column(name = "refund_fee_amount")
    private BigDecimal refundFeeAmount = BigDecimal.ZERO;

    @Column(name = "refund_amount")
    private BigDecimal refundAmount = BigDecimal.ZERO;

    private String currencyCode;

    private String status;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "contact_phone_code")
    private String contactPhoneCode;

    @Column(name = "contact_fullname")
    private String contactFullname;

    @Column(name = "commision_amount")
    private BigDecimal commisionAmount = BigDecimal.ZERO;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
