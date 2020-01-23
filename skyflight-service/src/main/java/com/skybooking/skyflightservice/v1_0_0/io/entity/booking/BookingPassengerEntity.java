package com.skybooking.skyflightservice.v1_0_0.io.entity.booking;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking_passengers")
@Data
public class BookingPassengerEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stakeholder_user_id")
    private Integer stakeholderUserId;

    @Column(name = "stakeholder_company_id")
    private Integer stakeholderCompanyId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "gender")
    private String gender;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "expire_date")
    private Date expireDate;

    @Column(name = "passenger_type")
    private String passengerType;

    @Column(name = "id_type")
    private Integer idType;

    @Column(name = "title")
    private String title;

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
