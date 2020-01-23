package com.skybooking.skyflightservice.v1_0_0.io.entity.booking;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "booking_payment_transactions")
@Data
public class BookingPaymentTransactionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "booking_id")
    private Integer bookingId;

    @Column(name = "slug")
    private String slug;

    @Column(name = "holder_name")
    private String holderName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "expire_date")
    private Date expireDate;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "description")
    private String description;

    @Column(name = "pipay_status")
    private String pipayStatus;

    @Column(name = "trans_id")
    private String transId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "processor_id")
    private String processorId;

    @Column(name = "digest")
    private String digest;

    @Column(name = "method")
    private String method;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "ipay88_status")
    private Integer ipay88Status;

    @Column(name = "auth_code")
    private String authCode;

    @Column(name = "signature")
    private String signature;

    @Column(name = "ipay88_payment_id")
    private String ipay88PaymentId;

    @Column(name = "payment_code")
    private String paymentCode;

    @Column(name = "status")
    private Integer status;

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
