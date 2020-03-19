package com.skybooking.skyflightservice.v1_0_0.io.entity.cabin;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

@Entity
@Table(name = "cabins")
@Data
public class CabinEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "cabin_code")
    private String cabinCode;

    @Column(name = "cabin_name")
    private String cabinName;

    @Column(name = "status")
    private Integer status;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private java.util.Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private java.util.Date updatedAt;

}
