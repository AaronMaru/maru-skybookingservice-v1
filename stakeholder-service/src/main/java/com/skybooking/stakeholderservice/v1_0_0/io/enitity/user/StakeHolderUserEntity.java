package com.skybooking.stakeholderservice.v1_0_0.io.enitity.user;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.contact.ContactEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.notification.NotificationEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.passenger.PassengerEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "stakeholder_users")
public class StakeHolderUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "stakeHolderUsers", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<StakeholderCompanyEntity> stakeholderCompanies;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "stakeHolderUserEntity", cascade = CascadeType.ALL)
    private List<ContactEntity> contactEntity;

    @OneToMany(mappedBy = "stakeHolderUserEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PassengerEntity> passengerEntities;

    @OneToMany(mappedBy = "stakeHolderUserEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationEntity> notification;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setIsSkyowner(int isSkyowner) {
        this.isSkyowner = isSkyowner;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ContactEntity> getContactEntities() {
        return contactEntity;
    }

    public void setContactEntities(List<ContactEntity> contactEntities) {
        this.contactEntity = contactEntities;
    }

    public Long getId() {
        return id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public List<ContactEntity> getContactEntity() {
        return contactEntity;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getSlug() {
        return slug;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoto() {
        return photo;
    }

    public int getIsSkyowner() {
        return isSkyowner;
    }

    public int getStatus() {
        if (status != null) {
            return status;
        }
        status = null;

        return status;
    }

    public int getCode() {
        return code;
    }

    public int getSameAsPresentAddress() {
        return sameAsPresentAddress;
    }

    public int getBookingStatus() {
        return bookingStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<PassengerEntity> getPassengerEntities() {
        return passengerEntities;
    }

    public void setPassengerEntities(List<PassengerEntity> passengerEntities) {
        this.passengerEntities = passengerEntities;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<StakeholderCompanyEntity> getStakeholderCompanies() {
        return stakeholderCompanies;
    }

    public void setStakeholderCompanies(List<StakeholderCompanyEntity> stakeholderCompanies) {
        this.stakeholderCompanies = stakeholderCompanies;
    }

    public List<NotificationEntity> getNotification() {
        return notification;
    }

    public void setNotification(List<NotificationEntity> notification) {
        this.notification = notification;
    }


}
