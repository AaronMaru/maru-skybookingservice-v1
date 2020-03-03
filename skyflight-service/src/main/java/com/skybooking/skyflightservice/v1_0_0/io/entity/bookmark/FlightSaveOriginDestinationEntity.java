package com.skybooking.skyflightservice.v1_0_0.io.entity.bookmark;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "flight_save_origin_destinations")
@Data
public class FlightSaveOriginDestinationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "elapsed_time")
    private Integer elapsedTime;

    @Column(name = "airline_code")
    private String airlineCode;

    @Column(name = "airline_name")
    private String airlineName;

    @Column(name = "airline_logo_45")
    private String airlineLogo45;

    @Column(name = "airline_logo_90")
    private String airlineLogo90;

    @Column(name = "d_date_time")
    private Date dDateTime;

    @Column(name = "a_date_time")
    private Date aDateTime;

    @Column(name = "o_location")
    private String oLocation;

    @Column(name = "d_location")
    private String dLocation;

    @Column(name = "o_location_name")
    private String oLocationName;

    @Column(name = "d_location_name")
    private String dLocationName;

    @Column(name = "flight_save_id")
    private Integer flightSaveId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "stop")
    private Integer stop;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;


}
