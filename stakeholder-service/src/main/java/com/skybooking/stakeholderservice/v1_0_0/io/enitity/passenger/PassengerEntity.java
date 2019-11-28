package com.skybooking.stakeholderservice.v1_0_0.io.enitity.passenger;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking_passengers")
@Data
public class PassengerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stakeholder_user_id", nullable = false)
    private StakeHolderUserEntity stakeHolderUserEntity;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthday")
    private Date birthDate;

    @Column(length = 20)
    private String gender;

    private String nationality;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "expire_date")
    private Date expireDate;

    @Column(name = "passenger_type")
    private String passengerType;

    @Column(name = "id_type")
    private Integer idType;

    private String title;

    private int status;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
