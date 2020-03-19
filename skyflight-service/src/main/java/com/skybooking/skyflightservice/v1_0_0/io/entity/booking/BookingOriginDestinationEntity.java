package com.skybooking.skyflightservice.v1_0_0.io.entity.booking;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking_origin_destinations")
@Data
public class BookingOriginDestinationEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "booking_id")
    private Integer bookingId;

    @Column(name = "elapsed_time")
    private Integer elapsedTime;

    @Column(name = "group_air_segs")
    private String groupAirSegs;

    @Column(name = "arrage_air_segs")
    private String arrageAirSegs;

    @Column(name = "multiple_air_status")
    private Integer multipleAirStatus;

    @Column(name = "multiple_air_url")
    private String multipleAirUrl;

    @Column(name = "stop")
    private Integer stop;

    @Column(name = "seat_remaining")
    private Integer seatRemaining;

    @Column(name = "cabin_code")
    private String cabinCode;

    @Column(name = "cabin_name")
    private String cabinName;

    @Column(name = "meal")
    private String meal;

    @Column(name = "dep_date")
    private Date depDate;

    @Column(name = "arr_date")
    private Date arrDate;

    @Column(name = "stop_qty")
    private Integer stopQty;

    @Column(name = "booking_code")
    private String bookingCode;

    @Column(name = "arr_timezone")
    private Integer arrTimezone;

    @Column(name = "dep_timezone")
    private Integer depTimezone;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "airline_code")
    private String airlineCode;

    @Column(name = "airline_name")
    private String airlineName;

    @Column(name = "operated_by_code")
    private String operatedByCode;

    @Column(name = "operated_by_name")
    private String operatedByName;

    @Column(name = "flight_number")
    private Integer flightNumber;

    @Column(name = "marriage_grp")
    private String marriageGrp;

    @Column(name = "equip_type")
    private String equipType;

    @Column(name = "air_craft_name")
    private String airCraftName;

    @Column(name = "dep_location_code")
    private String depLocationCode;

    @Column(name = "dep_airport_name")
    private String depAirportName;

    @Column(name = "dep_terminal")
    private String depTerminal;

    @Column(name = "dep_city")
    private String depCity;

    @Column(name = "dep_latitude")
    private String depLatitude;

    @Column(name = "dep_longitude")
    private String depLongitude;

    @Column(name = "arr_location_code")
    private String arrLocationCode;

    @Column(name = "arr_airport_name")
    private String arrAirportName;

    @Column(name = "arr_terminal")
    private String arrTerminal;

    @Column(name = "arr_city")
    private String arrCity;

    @Column(name = "arr_latitude")
    private String arrLatitude;

    @Column(name = "arr_longitude")
    private String arrLongitude;

    @Column(name = "layover")
    private Integer layover;

    @Column(name = "adjustment_date")
    private Integer adjustmentDate = 0;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "airline_ref")
    private String airlineRef;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
