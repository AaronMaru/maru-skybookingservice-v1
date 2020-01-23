package com.skybooking.skyflightservice.v1_0_0.io.entity.booking;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking_special_services")
@Data
public class BookingSpecialServiceEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "booking_id")
    private Integer bookingId;

    @Column(name = "rph")
    private Integer rph;

    @Column(name = "type")
    private String type;

    @Column(name = "ssr_code")
    private String ssrCode;

    @Column(name = "airline_code")
    private String airlineCode;

    @Column(name = "remark")
    private String remark;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
