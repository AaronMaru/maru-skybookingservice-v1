package com.skybooking.skyhotelservice.v1_0_0.io.repository.recentsearch;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.recentsearch.RecentSearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RecentSearchRP extends JpaRepository<RecentSearchEntity, Integer> {

    @Query(value = "SELECT * FROM hotel_recent_search WHERE destination_code like :destinationCode AND stakeholder_user_id = :userId AND stakeholder_company_id = :companyId", nativeQuery = true)
    RecentSearchEntity existsRecentSearch(String destinationCode, Integer userId, Integer companyId);

    @Query(value = "SELECT * FROM hotel_recent_search WHERE stakeholder_user_id = :userId AND stakeholder_company_id = :companyId LIMIT 5", nativeQuery = true)
    List<RecentSearchEntity> listRecentSearch(Integer userId, Integer companyId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM hotel_recent_search WHERE stakeholder_user_id = :userId AND stakeholder_company_id = :companyId", nativeQuery = true)
    void clearRecentSearch(Integer userId, Integer companyId);
}
