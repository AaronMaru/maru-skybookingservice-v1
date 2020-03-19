package com.skybooking.skyflightservice.v1_0_0.io.entity.user;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "userEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private StakeHolderUserEntity stakeHolderUser;

    @Column(nullable = true)
    private String slug;

    @Column(nullable = true)
    private String username;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true, length = 45)
    private String phone;

    @Column(nullable = true, length = 30)
    private String code;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true, name = "changed_password")
    private int changedPassword = 1;

    @Column(nullable = true, length = 150, name = "provider_id")
    private String providerId;

    @Column(nullable = true, length = 45)
    private String provider;

    private int verified;

    @Column(nullable = true, length = 150, name = "user_type")
    private String userType = "officestaff";

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login_at")
    private Date lastLoginAt;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "notify_booking")
    private int notifyBooking = 1;

    @Column(name = "notify_email")
    private int notifyEmail = 1;

    @Column(name = "allow_notify")
    private int allowNotify = 1;

}
