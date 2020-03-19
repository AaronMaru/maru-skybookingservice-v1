package com.skybooking.paymentservice.v1_0_0.io.enitity.user;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stakeholder_users")
@Data
public class StakeHolderUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(length = 36)
    private String uuid;

    @Column(length = 45)
    private String slug;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(length = 20)
    private String gender;

    private String photo;

    @Column(name = "is_skyowner")
    private int isSkyowner = 0;

    private Integer status;

    private int code = 0;

    @Column(name = "same_as_present_address")
    private int sameAsPresentAddress = 0;

    @Column(name = "booking_status")
    private int bookingStatus = 1;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "created_from")
    private String createdFrom;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private String nationality;

    @Column(name = "currency_id")
    private Long currencyId;
}
