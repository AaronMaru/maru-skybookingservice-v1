package com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.page;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "frontend_pages")
@Data
public class FrontendPageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Column(name = "meta_title")
    private String metaTitle;

    @Column(name = "meta_description")
    @Lob
    private String metaDescription;

    @Column(name = "meta_keyword")
    private String metaKeyword;

    @Column(name = "public")
    private Integer allowPublic;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
