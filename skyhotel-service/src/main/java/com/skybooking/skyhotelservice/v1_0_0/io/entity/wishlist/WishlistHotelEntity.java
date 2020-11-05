package com.skybooking.skyhotelservice.v1_0_0.io.entity.wishlist;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "hotel_wishlist")
public class WishlistHotelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stakeholder_user_id")
    private Long stakeholderUserId;

    @Column(name = "stakeholder_company_id")
    private Long stakeholderCompanyId;

    @Column(name = "hotel_code")
    private Integer hotelCode;

    private Boolean status = false;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "view_count")
    private Integer viewCount = 1;

    private Float review;

    private String type = "SAVE";

    private BigDecimal price = BigDecimal.ZERO;

    private String currency;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public WishlistHotelEntity() {}

    public WishlistHotelEntity(Long stakeholderUserId, Long stakeholderCompanyId, Integer hotelCode) {
        this.stakeholderUserId = stakeholderUserId;
        this.stakeholderCompanyId = stakeholderCompanyId;
        this.hotelCode = hotelCode;
    }

}
