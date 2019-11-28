package com.skybooking.stakeholderservice.v1_0_0.io.enitity.company;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stakeholder_user_has_companies")
public class StakeholderUserHasCompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stakeholder_user_id")
    private Long stakeholderUserId;

    @Column(name = "stakeholder_company_id")
    private Long stakeholderCompanyId;

    @Column(name = "skyuser_role")
    private String skyuserRole;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStakeholderUserId() {
        return stakeholderUserId;
    }

    public void setStakeholderUserId(Long stakeholderUserId) {
        this.stakeholderUserId = stakeholderUserId;
    }

    public Long getStakeholderCompanyId() {
        return stakeholderCompanyId;
    }

    public void setStakeholderCompanyId(Long stakeholderCompanyId) {
        this.stakeholderCompanyId = stakeholderCompanyId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSkyuserRole() {
        return skyuserRole;
    }

    public void setSkyuserRole(String skyuserRole) {
        this.skyuserRole = skyuserRole;
    }
}
