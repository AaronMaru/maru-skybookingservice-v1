package com.skybooking.eventservice.v1_0_0.io.entity.user;

import com.skybooking.eventservice.v1_0_0.io.entity.company.StakeholderCompanyEntity;
import com.skybooking.eventservice.v1_0_0.io.entity.contact.ContactEntity;
import com.skybooking.eventservice.v1_0_0.io.entity.passenger.PassengerEntity;
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

    @ManyToMany(mappedBy = "stakeHolderUsers", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StakeholderCompanyEntity> stakeholderCompanies;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "stakeHolderUserEntity", cascade = CascadeType.ALL)
    private List<ContactEntity> contactEntity;

    @OneToMany(mappedBy = "stakeHolderUserEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PassengerEntity> passengerEntities;

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

    private final int code = 0;

    @Column(name = "same_as_present_address")
    private final int sameAsPresentAddress = 0;

    @Column(name = "booking_status")
    private final int bookingStatus = 1;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "created_from")
    private String createdFrom;

    @Column(name = "device_name")
    private String deviceName;

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

    public List<ContactEntity> getContactEntities() {
        return contactEntity;
    }

    public void setContactEntities(List<ContactEntity> contactEntities) {
        this.contactEntity = contactEntities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<ContactEntity> getContactEntity() {
        return contactEntity;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getIsSkyowner() {
        return isSkyowner;
    }

    public void setIsSkyowner(int isSkyowner) {
        this.isSkyowner = isSkyowner;
    }

    public int getStatus() {
        if (status != null) {
            return status;
        }
        status = 0;

        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(String createdFrom) {
        this.createdFrom = createdFrom;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

}
