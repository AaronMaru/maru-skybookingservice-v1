package com.skybooking.stakeholderservice.v1_0_0.io.enitity.user;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stakeholder_user_invitations")
public class StakeholderUserInvitationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slug")
    private String slug;

    @Column(name = "invite_stakeholder_user_id")
    private Long inviteStakeholderUserId;

    @Column(name = "invite_from")
    private Long inviteFrom;

    @Column(name = "invite_to")
    private String inviteTo;

    private Integer status;

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

    @Column(name = "stakeholder_company_id")
    private Long stakeholderCompanyId;

    @Column(name = "skyuser_role")
    private String skyuserRole;

    public Long getId() {
        return id;
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

    public Long getInviteStakeholderUserId() {
        return inviteStakeholderUserId;
    }

    public void setInviteStakeholderUserId(Long inviteStakeholderUserId) {
        this.inviteStakeholderUserId = inviteStakeholderUserId;
    }

    public Long getInviteFrom() {
        return inviteFrom;
    }

    public void setInviteFrom(Long inviteFrom) {
        this.inviteFrom = inviteFrom;
    }

    public String getInviteTo() {
        return this.inviteTo;
    }

    public void setInviteTo(String inviteTo) {
        this.inviteTo = inviteTo;
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

    public Long getStakeholderCompanyId() {
        return stakeholderCompanyId;
    }

    public void setStakeholderCompanyId(Long stakeholderCompanyId) {
        this.stakeholderCompanyId = stakeholderCompanyId;
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
