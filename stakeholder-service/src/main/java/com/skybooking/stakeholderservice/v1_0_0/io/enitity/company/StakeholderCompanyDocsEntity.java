package com.skybooking.stakeholderservice.v1_0_0.io.enitity.company;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.WhereJoinTable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stakeholder_company_docs")
public class StakeholderCompanyDocsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stcompany_id", nullable = false)
    private StakeholderCompanyEntity stakeholderCompany;

    @Column(name = "image")
    @Lob
    private String image;

    @Column(name = "type")
    private String type = "license";

    @Column(name = "status")
    private Integer status = 1;

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


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

//    public StakeholderCompanyEntity getStakeholderCompany() {
//        return stakeholderCompany;
//    }

    public void setStakeholderCompany(StakeholderCompanyEntity stakeholderCompany) {
        this.stakeholderCompany = stakeholderCompany;
    }
}
