package com.skybooking.stakeholderservice.v1_0_0.io.enitity.contact;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.WhereJoinTable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "contacts")
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String value;
    private int priority = 0;

    @Column(name = "contactable_type")
    private String contactableType = "skyuser";

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contactable_id", nullable = false)
    @WhereJoinTable(clause = "contactable_type = 'skyuser'")
    private StakeHolderUserEntity stakeHolderUserEntity;


    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
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
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getContactableType() {
        return contactableType;
    }

    public void setContactableType(String contactableType) {
        this.contactableType = contactableType;
    }

    public StakeHolderUserEntity getStakeHolderUserEntity(StakeHolderUserEntity stkUser) {
        return stakeHolderUserEntity;
    }

    public void setStakeHolderUserEntity(StakeHolderUserEntity stakeHolderUserEntity) {
        this.stakeHolderUserEntity = stakeHolderUserEntity;
    }

}
