package com.skybooking.eventservice.v1_0_0.io.entity.company;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "stakeholder_company_docs_locale")
public class StakeholderCompanyDocsLocaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type = "license";

    @Column(name = "locale_id")
    private Long localeId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stcompany_doc_id", nullable = false)
    private StakeholderCompanyDocsEntity companyDocs;

    @Column(name = "doc_name")
    private String docName;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


}
