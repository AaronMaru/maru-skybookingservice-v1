package com.skybooking.skygatewayservice.io.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String slug;

    private String username;

    private String email;

    @Column(length = 45)
    private String phone;

    @Column(length = 30)
    private String code;

    private String password;

    @Column(name = "changed_password")
    private int changedPassword = 1;

    @Column(length = 150, name = "provider_id")
    private String providerId;

    @Column(length = 45)
    private String provider;

    private int verified;

    @Column(length = 150, name = "user_type")
    private String userType = "skyuser";

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
