package com.skybooking.backofficeservice.v1_0_0.io.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "hotel_popular_city")
public class QuickEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "destination_code")
    private String destinationCode;

    @Column(name = "destination_type")
    private String destinationType;

    @Column(name = "type")
    private String type;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

}
