package com.skybooking.skyhotelservice.v1_0_0.io.repository.recentsearch;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.recentsearch.RecentSearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecentSearchRP extends JpaRepository<RecentSearchEntity, Long> {

    @Query(value = "SELECT * FROM hotel_recent_search WHERE (CASE WHEN ?2 <> 0 THEN stakeholder_company_id = ?2 ELSE stakeholder_company_id = 0 AND stakeholder_user_id = ?1 END) AND destination_code = ?3", nativeQuery = true)
    Optional<RecentSearchEntity> existsRecentSearch(Long skyuserId, Long companyId, String destinationCode);

    @Query(value = "SELECT * FROM hotel_recent_search WHERE (CASE WHEN ?2 <> 0 THEN stakeholder_company_id = ?2 ELSE stakeholder_company_id = 0 AND stakeholder_user_id = ?1 END)", nativeQuery = true)
    List<RecentSearchEntity> listRecentSearch(Long skyuserId, Long companyId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM hotel_recent_search WHERE (CASE WHEN ?2 <> 0 THEN stakeholder_company_id = ?2 ELSE stakeholder_company_id = 0 AND stakeholder_user_id = ?1 END)", nativeQuery = true)
    void clearRecentSearch(Long skyuserId, Long companyId);
}
