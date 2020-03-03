package com.skybooking.skyflightservice.v1_0_0.io.entity.bookmark;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "flight_saves")
@Data
public class FlightSavesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "trip_type")
    private String tripType;

    @Column(name = "class_code")
    private String classCode;

    @Column(name = "class_name")
    private String className;

    @Column(name = "adult")
    private Integer adult;

    @Column(name = "child")
    private Integer child;

    @Column(name = "infant")
    private Integer infant;

    @Column(name = "stop_search")
    private Integer stopSearch;

    @Column(name = "multiple_air_status")
    private Integer multipleAirStatus;

    @Column(name = "multiple_air_logo_90")
    private String multipleAirLogo90;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "tag_id")
    private String tagId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "decimal_places")
    private Integer decimalPlaces;

    @Column(name = "base_fare")
    private BigDecimal baseFare;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

}
