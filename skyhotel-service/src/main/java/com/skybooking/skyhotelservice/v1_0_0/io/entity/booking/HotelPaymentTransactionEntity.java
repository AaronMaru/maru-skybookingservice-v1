package com.skybooking.skyhotelservice.v1_0_0.io.entity.booking;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "payment_transaction")
public class HotelPaymentTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "payment_rule_code")
    private String paymentRuleCode;

    @Column(name = "transaction_code")
    private String transactionCode;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Column(name = "pay_date")
    private Date payDate;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "amount")
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(name = "currency_code")
    private String currencyCode;

    private int status;

    private String description;

    @Column(name = "pipay_status")
    private String pipayStatus;

    @Column(name = "payment_code")
    private String paymentCode;

    @Column(name = "ipay88_status")
    private Integer ipay88Status;

    @Column(name = "ipay88_payment_id")
    private String ipay88PaymentId;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "processor_id")
    private String processorId;

    private String digest;

    @Column(name = "auth_code")
    private String authCode;

    private String signature;

    @Column(name = "booking_type")
    private String bookingType = "HOTEL";

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
