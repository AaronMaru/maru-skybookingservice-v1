package com.skybooking.skyhotelservice.v1_0_0.io.entity.popularcity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "hotel_popular_city")
public class HotelPopularCityEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "destination_code")
    private String destinationCode;

    @Column(name = "destination_type")
    private String destinationType;

    private String type;

    @Column(name = "thumbnail")
    private String thumbnail;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
