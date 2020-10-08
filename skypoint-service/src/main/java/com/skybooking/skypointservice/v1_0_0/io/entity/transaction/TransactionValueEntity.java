package com.skybooking.skypointservice.v1_0_0.io.entity.transaction;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "transaction_values")
public class TransactionValueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "code")
    private String code;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "extra_rate")
    private BigDecimal extraRate;

    @Column(name = "earning_amount")
    private BigDecimal earningAmount;

    @Column(name = "transaction_type_code")
    private String transactionTypeCode;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
