package com.skybooking.skyflightservice.v1_0_0.io.entity.booking;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "booking_travel_itineraries")
@Data
public class BookingTravelItineraryEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "booking_id")
    private Integer bookingId;

    @Column(name = "pass_type")
    private String passType;

    @Column(name = "pass_qty")
    private Integer passQty;

    @Column(name = "private_fare")
    private String privateFare;

    @Column(name = "base_fare")
    private BigDecimal baseFare;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "total_tax")
    private BigDecimal totalTax;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "non_refundable_ind")
    private String nonRefundableInd;

    @Column(name = "baggage_info")
    private String baggageInfo;

    @Column(name = "fare_calculation")
    private String fareCalculation;

    @Column(name = "endorsements")
    private String endorsements;

    @Column(name = "noted")
    private String noted;

    @Column(name = "piece_status")
    private Integer pieceStatus;

    @Column(name = "bag_airline")
    private String bagAirline;

    @Column(name = "bag_piece")
    private Integer bagPiece;

    @Column(name = "bag_weight")
    private Integer bagWeight;

    @Column(name = "bag_unit")
    private String bagUnit;

    @Column(name = "com_percentage")
    private BigDecimal comPercentage;

    @Column(name = "com_amount")
    private BigDecimal comAmount;

    @Column(name = "com_mkamount")
    private BigDecimal comMkamount;

    @Column(name = "com_source")
    private String comSource;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date updatedAt;

}
