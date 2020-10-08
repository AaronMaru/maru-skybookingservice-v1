package com.skybooking.skypointservice.v1_0_0.io.entity.account;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "type")
    private String type;

    @Column(name = "topup")

    private BigDecimal topup = BigDecimal.ZERO;

    @Column(name = "withdrawal")
    private BigDecimal withdrawal = BigDecimal.ZERO;

    @Column(name = "earning")
    private BigDecimal earning = BigDecimal.ZERO;

    @Column(name = "earning_extra")
    private BigDecimal earningExtra = BigDecimal.ZERO;

    @Column(name = "refund")
    private BigDecimal refund = BigDecimal.ZERO;

    @Column(name = "balance")
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "saved_point")
    private BigDecimal savedPoint = BigDecimal.ZERO;

    @Column(name = "level_code")
    private String levelCode;

    @Column(name = "status")
    private String status = "active";

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public AccountEntity(String levelCode) {
        this.levelCode = levelCode;
    }

    public AccountEntity() {
    }

    public AccountEntity(Integer id) {
        this.id = id;
    }
}
