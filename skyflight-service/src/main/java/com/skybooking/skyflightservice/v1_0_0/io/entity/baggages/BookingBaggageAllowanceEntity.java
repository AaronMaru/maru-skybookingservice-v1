package com.skybooking.skyflightservice.v1_0_0.io.entity.baggages;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking_baggage_allowances")
@Data
public class BookingBaggageAllowanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "baggage_id")
    private Integer baggage_id;

    @Column(name = "pass_type")
    private String passType;

    @Column(name = "airline_code")
    private String airlineCode;

    @Column(name = "is_piece")
    private byte isPiece;

    @Column(name = "pieces")
    private int pieces;

    @Column(name = "weight")
    private int weight;

    @Column(name = "non_refundable")
    private Boolean nonRefundable;

    @Column(name = "unit")
    private String unit;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
