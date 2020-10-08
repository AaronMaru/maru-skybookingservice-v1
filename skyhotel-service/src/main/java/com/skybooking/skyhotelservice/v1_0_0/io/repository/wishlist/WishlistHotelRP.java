package com.skybooking.skyhotelservice.v1_0_0.io.repository.wishlist;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.wishlist.WishlistHotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistHotelRP extends JpaRepository<WishlistHotelEntity, Long> {
    @Query(value = "SELECT * FROM hotel_wishlist WHERE (CASE WHEN ?2 IS NOT NULL THEN stakeholder_company_id = ?2 ELSE stakeholder_company_id IS NULL AND stakeholder_user_id = ?1 END) AND status = 1 AND type = ?3", nativeQuery = true)
    List<WishlistHotelEntity> findBySkyuserIdAndCompanyId(Long skyuserId, Long companyId, String type);

    @Query(value = "SELECT * FROM hotel_wishlist WHERE (CASE WHEN ?2 IS NOT NULL THEN stakeholder_company_id = ?2 ELSE stakeholder_company_id IS NULL AND stakeholder_user_id = ?1 END) AND hotel_code = ?3 AND type = ?4", nativeQuery = true)
    Optional<WishlistHotelEntity> findHotelViewedAndSave(Long skyuserId, Long companyId, Integer hotelCode, String type);

}
