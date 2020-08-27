package com.skybooking.skyhotelservice.v1_0_0.io.repository.viewedHotel;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.viewedhotel.HotelViewedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewedHotelRP extends JpaRepository<HotelViewedEntity, Long> {
    @Query(value = "SELECT * FROM hotel_viewed WHERE (CASE WHEN ?2 IS NOT NULL THEN stakeholder_company_id = ?2 ELSE stakeholder_company_id IS NULL AND stakeholder_user_id = ?1 END)", nativeQuery = true)
    List<HotelViewedEntity> findBySkyuserIdAndCompanyId(Long skyuserId, Long companyId);
}
