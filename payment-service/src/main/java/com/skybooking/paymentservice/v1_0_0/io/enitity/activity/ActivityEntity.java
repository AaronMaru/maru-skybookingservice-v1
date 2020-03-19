package com.skybooking.paymentservice.v1_0_0.io.enitity.activity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "activity_log")
@Data
public class ActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int type = 2;

    @Column(name = "log_name")
    private String logName;
    private String description;

    @Column(name = "subject_id")
    private Integer subjectId;

    @Column(name = "subject_type")
    private String subjectType;

    @Column(name = "causer_id")
    private Long causerId;

    @Column(name = "causer_type")
    private String causerType;

    private String properties = "[]";

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
