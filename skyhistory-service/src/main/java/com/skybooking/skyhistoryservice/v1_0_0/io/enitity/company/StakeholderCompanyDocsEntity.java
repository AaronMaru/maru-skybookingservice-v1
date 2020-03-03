package com.skybooking.skyhistoryservice.v1_0_0.io.enitity.company;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(name = "file_name")
    @Lob
    private String fileName;

    @Column(name = "type")
    private String type = "license";

    @Column(name = "doc_name")
    private String docName;

    @Column(name = "is_required")
    private Integer isRequired;

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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
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

//    public StakeholderCompanyEntity getStakeholderCompany() {
//        return stakeholderCompany;
//    }

    public void setStakeholderCompany(StakeholderCompanyEntity stakeholderCompany) {
        this.stakeholderCompany = stakeholderCompany;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Integer getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
    }

    public StakeholderCompanyEntity getStakeholderCompany() {
        return stakeholderCompany;
    }
}
