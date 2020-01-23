package com.skybooking.skyflightservice.v1_0_0.io.entity.booking;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "booking_fare_breakdowns")
@Data
public class BookingFareBreakdownEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "travel_itin_id")
    private Integer travelItinId;

    @Column(name = "cabin")
    private String cabin;

    @Column(name = "code")
    private String code;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "pass_type")
    private String passType;

    @Column(name = "fare_type")
    private String fareType;

    @Column(name = "filing_carrier")
    private String filingCarrier;

    @Column(name = "global_ind")
    private String globalInd;

    @Column(name = "market")
    private String market;

    @Column(name = "bag_allowance")
    private String bagAllowance;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
