package com.skybooking.skypointservice.v1_0_0.io.entity.pointLimit;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "skypoint_transaction")
public class SkyPointTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "stakeholder_user_id")
    private Integer stakeholderUserId;

    @Column(name = "stakeholder_company_id")
    private Integer stakeholderCompanyId;

    @Column(name = "status")
    private Boolean status = true;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
