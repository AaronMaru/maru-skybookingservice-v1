package com.skybooking.skyhotelservice.v1_0_0.io.entity.savedhotel;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "hotel_wishlist")
public class SavedHotelEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "stakeholder_user_id")
    private Long stakeholderUserId;

    @Column(name = "stakeholder_company_id")
    private Long stakeholderCompanyId;

    @Column(name = "hotel_code")
    private Integer hotelCode;

    private Boolean status = false;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public SavedHotelEntity() {}

    public SavedHotelEntity(Long stakeholderUserId, Long stakeholderCompanyId, Integer hotelCode) {
        this.stakeholderUserId = stakeholderUserId;
        this.stakeholderCompanyId = stakeholderCompanyId;
        this.hotelCode = hotelCode;
    }

}
