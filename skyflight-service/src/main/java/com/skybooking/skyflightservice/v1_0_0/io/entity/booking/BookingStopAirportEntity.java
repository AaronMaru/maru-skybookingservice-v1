package com.skybooking.skyflightservice.v1_0_0.io.entity.booking;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking_stop_airports")
@Data
public class BookingStopAirportEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "booking_od_id")
    private Integer bookingOdId;

    @Column(name = "elapsed_time")
    private Integer elapsedTime;

    @Column(name = "location_code")
    private String locationCode;

    @Column(name = "city")
    private String city;

    @Column(name = "location")
    private String location;

    @Column(name = "arr_date")
    private Date arrDate;

    @Column(name = "dep_date")
    private Date depDate;

    @Column(name = "duration")
    private String duration;

    @Column(name = "gmt_offset")
    private Integer gmtOffset;

    @Column(name = "equipment")
    private String equipment;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
