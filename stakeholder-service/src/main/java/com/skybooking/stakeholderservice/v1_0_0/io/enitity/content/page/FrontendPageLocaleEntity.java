package com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.page;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "frontend_page_locales")
@Data
public class FrontendPageLocaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "page_id")
    private Long pageId;

    @Column(name = "locale_id")
    private Long localeId;

    private String locale;

    private String title;

    @Lob
    private String body;

    @Column(name = "updated_by")
    private BigInteger updatedBy;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
