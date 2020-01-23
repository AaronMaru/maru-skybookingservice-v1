package com.skybooking.skyflightservice.v1_0_0.io.entity.booking;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "booking_airtickets")
@Data
public class BookingAirTicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "booking_id")
    private Integer bookingId;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "gender")
    private String gender;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "decimal_place")
    private String decimalPlace;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "issuing_location")
    private String issuingLocation;

    @Column(name = "pass_type")
    private String passType;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "id_type")
    private Integer idType;

    @Column(name = "status")
    private Integer status;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
