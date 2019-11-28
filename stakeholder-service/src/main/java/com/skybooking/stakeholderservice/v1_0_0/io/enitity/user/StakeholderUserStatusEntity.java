package com.skybooking.stakeholderservice.v1_0_0.io.enitity.user;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stakeholder_user_status")
public class StakeholderUserStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer status;

    @Lob
    private String comment;

    @Column(name = "actionable_id")
    private Long actionableId;

    @Column(name = "actionable_type")
    private String actionableType = "user";

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "skyuser_id")
    private Long skyuserId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getActionableId() {
        return actionableId;
    }

    public void setActionableId(Long actionableId) {
        this.actionableId = actionableId;
    }

    public String getActionableType() {
        return actionableType;
    }

    public void setActionableType(String actionableType) {
        this.actionableType = actionableType;
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

    public Long getSkyuserId() {
        return this.skyuserId;
    }

    public void setSkyuserId(Long skyuserId) {
        this.skyuserId = skyuserId;
    }
}
