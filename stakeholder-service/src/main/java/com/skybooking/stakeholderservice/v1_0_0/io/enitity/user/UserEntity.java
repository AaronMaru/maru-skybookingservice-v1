package com.skybooking.stakeholderservice.v1_0_0.io.enitity.user;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "userEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private StakeHolderUserEntity stakeHolderUser;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VerifyUserEntity> verifyUserEntity;

//    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<UserTokenApiEntity> userTokenEntities;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getChangedPassword() {
        return changedPassword;
    }

    public void setChangedPassword(int changedPassword) {
        this.changedPassword = changedPassword;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public int getNotifyBooking() {
        return notifyBooking;
    }

    public void setNotifyBooking(int notifyBooking) {
        this.notifyBooking = notifyBooking;
    }

    public int getNotifyEmail() {
        return notifyEmail;
    }

    public void setNotifyEmail(int notifyEmail) {
        this.notifyEmail = notifyEmail;
    }

    public int getAllowNotify() {
        return allowNotify;
    }

    public void setAllowNotify(int allowNotify) {
        this.allowNotify = allowNotify;
    }

    public StakeHolderUserEntity getStakeHolderUser() {
        return stakeHolderUser;
    }

    public void setStakeHolderUser(StakeHolderUserEntity stakeHolderUser) {
        this.stakeHolderUser = stakeHolderUser;
    }

    public List<VerifyUserEntity> getVerifyUserEntity() {
        return verifyUserEntity;
    }

    public void setVerifyUserEntity(List<VerifyUserEntity> verifyUserEntity) {
        this.verifyUserEntity = verifyUserEntity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

//    public List<UserTokenApiEntity> getUserTokenEntities() {
//        return userTokenEntities;
//    }
//
//    public void setUserTokenEntities(List<UserTokenApiEntity> userTokenEntities) {
//        this.userTokenEntities = userTokenEntities;
//    }

}
