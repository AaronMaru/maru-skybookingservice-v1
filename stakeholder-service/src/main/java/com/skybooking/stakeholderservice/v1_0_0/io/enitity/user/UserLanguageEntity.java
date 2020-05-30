package com.skybooking.stakeholderservice.v1_0_0.io.enitity.user;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "user_language")
public class UserLanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stakeholder_user_id")
    private Long skyuserId;

    @Column(name = "stakeholder_company_id")
    private Long companyId;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "locale_key")
    private String localeKey;

    private Integer status = 1;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
