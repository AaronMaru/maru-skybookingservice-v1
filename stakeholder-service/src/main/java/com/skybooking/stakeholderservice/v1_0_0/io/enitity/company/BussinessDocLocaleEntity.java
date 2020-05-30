package com.skybooking.stakeholderservice.v1_0_0.io.enitity.company;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "business_doc_locale")
public class BussinessDocLocaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "locale_id")
    private Long localeId;

    @Column(name = "business_doc_id")
    private Long businessDocId;

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

}
