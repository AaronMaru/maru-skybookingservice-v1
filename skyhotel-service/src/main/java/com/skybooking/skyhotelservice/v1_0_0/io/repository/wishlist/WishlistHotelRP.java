package com.skybooking.skyhotelservice.v1_0_0.io.repository.wishlist;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.savedhotel.SavedHotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistHotelRP extends JpaRepository<SavedHotelEntity, Long> {
    @Query(value = "SELECT * FROM hotel_wishlist WHERE (CASE WHEN ?2 IS NOT NULL THEN stakeholder_company_id = ?2 ELSE stakeholder_company_id IS NULL AND stakeholder_user_id = ?1 END) AND status = 1", nativeQuery = true)
    List<SavedHotelEntity> findBySkyuserIdAndCompanyId(Long skyuserId, Long companyId);

    @Query(value = "SELECT * FROM hotel_wishlist WHERE (CASE WHEN ?2 IS NOT NULL THEN stakeholder_company_id = ?2 ELSE stakeholder_company_id IS NULL AND stakeholder_user_id = ?1 END) AND hotel_code = ?3", nativeQuery = true)
    Optional<SavedHotelEntity> findBySkyuserIdAndCompanyIdAndHotelCode(Long skyuserId, Long companyId, Integer hotelCode);
}
