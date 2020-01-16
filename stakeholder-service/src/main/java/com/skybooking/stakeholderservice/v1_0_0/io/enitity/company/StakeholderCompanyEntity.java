package com.skybooking.stakeholderservice.v1_0_0.io.enitity.company;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.contact.ContactEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "stakeholder_companies")
public class StakeholderCompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(name = "stakeholder_user_has_companies",
    joinColumns = @JoinColumn(name = "stakeholder_company_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "stakeholder_user_id", referencedColumnName = "id"))
    private List<StakeHolderUserEntity> stakeHolderUsers;

    @OneToMany(mappedBy = "stakeholderCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StakeholderCompanyDocsEntity> stakeholderCompanyDocs;

    @Column(name = "slug")
    private String slug;

    @Column(name = "icon")
    private String icon;

    @Column(name = "profile_img")
    @Lob
    private String profileImg;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "employee_no")
    private String employeeNo;

    @Column(name = "description")
    @Lob
    private String description;

    @Column(name = "verified")
    private Integer verified = 0;

    @Column(name = "user_validator_id")
    private Integer userValidatorId = 0;

    @Column(name = "status")
    private Integer status = 0;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Column(name = "deleted_at")
    private java.util.Date deletedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "notify_expired")
    private java.sql.Date notifyExpired;

    @Column(name = "notify_booking")
    private Integer notifyBooking = 1;

    @Column(name = "notify_email")
    private Integer notifyEmail = 1;

    @Column(name = "allow_notify")
    private Integer allowNotify = 1;

    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "created_from")
    private String createdFrom;

    @Column(name = "bussiness_type_id")
    private Long bussinessTypeId;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_position")
    private String contactPosition;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmployeeNo() {
        return this.employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public Integer getVerified() {
        return this.verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }

    public Integer getUserValidatorId() {
        return this.userValidatorId;
    }

    public void setUserValidatorId(Integer userValidatorId) {
        this.userValidatorId = userValidatorId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDeletedBy() {
        return this.deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public java.util.Date getDeletedAt() {
        return this.deletedAt;
    }

    public void setDeletedAt(java.util.Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public java.util.Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    public java.util.Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public java.sql.Date getNotifyExpired() {
        return this.notifyExpired;
    }

    public void setNotifyExpired(java.sql.Date notifyExpired) {
        this.notifyExpired = notifyExpired;
    }

    public Integer getNotifyBooking() {
        return this.notifyBooking;
    }

    public void setNotifyBooking(Integer notifyBooking) {
        this.notifyBooking = notifyBooking;
    }

    public Integer getNotifyEmail() {
        return this.notifyEmail;
    }

    public void setNotifyEmail(Integer notifyEmail) {
        this.notifyEmail = notifyEmail;
    }

    public Integer getAllowNotify() {
        return this.allowNotify;
    }

    public void setAllowNotify(Integer allowNotify) {
        this.allowNotify = allowNotify;
    }

    public String getCompanyCode() {
        return this.companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCreatedFrom() {
        return this.createdFrom;
    }

    public void setCreatedFrom(String createdFrom) {
        this.createdFrom = createdFrom;
    }

    public Long getBussinessTypeId() {
        return bussinessTypeId;
    }

    public void setBussinessTypeId(Long bussinessTypeId) {
        this.bussinessTypeId = bussinessTypeId;
    }

    public String getContactPerson() {
        return this.contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPosition() {
        return this.contactPosition;
    }

    public void setContactPosition(String contactPosition) {
        this.contactPosition = contactPosition;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStakeHolderUsers(List<StakeHolderUserEntity> stakeHolderUsers) {
        this.stakeHolderUsers = stakeHolderUsers;
    }

    public Set<StakeholderCompanyDocsEntity> getStakeholderCompanyDocs() {
        return stakeholderCompanyDocs;
    }

    public void setStakeholderCompanyDocsSet(Set<StakeholderCompanyDocsEntity> stakeholderCompanyDocs) {
        this.stakeholderCompanyDocs.clear();
        this.stakeholderCompanyDocs.addAll(stakeholderCompanyDocs);
    }

    public void setStakeholderCompanyDocPut(Set<StakeholderCompanyDocsEntity> stakeholderCompanyDocs) {
        this.stakeholderCompanyDocs = stakeholderCompanyDocs;
    }


}
