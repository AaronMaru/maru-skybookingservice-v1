package com.skybooking.skyflightservice.v1_0_0.io.entity.meal;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

@Entity
@Table(name = "meals")
@Data
public class MealEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "meal_code")
    private String mealCode;

    @Column(name = "meal_name")
    private String mealName;

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
