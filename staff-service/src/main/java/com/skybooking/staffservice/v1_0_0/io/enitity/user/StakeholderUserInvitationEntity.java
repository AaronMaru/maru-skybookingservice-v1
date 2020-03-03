package com.skybooking.staffservice.v1_0_0.io.enitity.user;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stakeholder_user_invitations")
public class StakeholderUserInvitationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "slug")
    private String slug;

    @Column(name = "invite_stakeholder_user_id")
    private Long inviteStakeholderUserId;

    @Column(name = "invite_from")
    private String inviteFrom;

    @Column(name = "invite_to")
    private String inviteTo;

    @Column(name = "deleted_at")
    private Date deletedAt;

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

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
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

    public String getInviteFrom() {
        return this.inviteFrom;
    }

    public void setInviteFrom(String inviteFrom) {
        this.inviteFrom = inviteFrom;
    }

    public String getInviteTo() {
        return this.inviteTo;
    }

    public void setInviteTo(String inviteTo) {
        this.inviteTo = inviteTo;
    }

    public Date getDeletedAt() {
        return this.deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getStakeholderCompanyId() {
        return stakeholderCompanyId;
    }

    public void setStakeholderCompanyId(Long stakeholderCompanyId) {
        this.stakeholderCompanyId = stakeholderCompanyId;
    }

    public String getSkyuserRole() {
        return skyuserRole;
    }

    public void setSkyuserRole(String skyuserRole) {
        this.skyuserRole = skyuserRole;
    }

}
