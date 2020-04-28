package com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.career;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "career_locales")
@Data
public class CareersLocaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "career_id")
    private Long careerId;

    private String locale;

    private String title;

    @Lob
    private String description;

    @Lob
    private String requirement;

    @Lob
    private String contact;

    @Lob
    private String responsibility;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


}
