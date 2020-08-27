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

    @Column(name = "booking_reference")
    private String bookingReference;

    @Column(name = "cancellation_reference")
    private String cancellationReference;

    @Column(name = "markup_amount")
    private BigDecimal markupAmount = BigDecimal.ZERO;

    @Column(name = "markup_percentage")
    private BigDecimal markupPercentage = BigDecimal.ZERO;

    @Column(name = "payment_fee_amount")
    private BigDecimal paymentFeeAmount = BigDecimal.ZERO;

    @Column(name = "payment_fee_percentage")
    private BigDecimal paymentFeePercentage = BigDecimal.ZERO;

    @Column(name = "base_payment_percentage")
    private BigDecimal basePaymentPercentage = BigDecimal.ZERO;

    @Column(name = "base_payment_amount")
    private BigDecimal basePaymentAmount = BigDecimal.ZERO;

    @Column(name = "dis_payment_percentage")
    private BigDecimal disPaymentPercentage = BigDecimal.ZERO;

    @Column(name = "dis_payment_amount")
    private BigDecimal disPaymentAmount = BigDecimal.ZERO;

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

    @Lob
    private String remark;

    @Column(name = "cont_email")
    private String contEmail;

    @Column(name = "cont_phone")
    private String contPhone;

    @Column(name = "cont_phone_code")
    private String contPhoneCode;

    @Column(name = "cont_fullname")
    private String contFullname;

    @Column(name = "cust_name")
    private String custName;

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
