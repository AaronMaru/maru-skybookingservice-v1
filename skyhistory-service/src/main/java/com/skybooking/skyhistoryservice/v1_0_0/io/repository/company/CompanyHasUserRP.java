package com.skybooking.skyhistoryservice.v1_0_0.io.repository.company;

import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.company.StakeholderUserHasCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyHasUserRP extends JpaRepository<StakeholderUserHasCompanyEntity, Long> {

    /**
     * Check exists user by phone
     */
    @Query(value = "SELECT (CASE WHEN (SELECT COUNT(stakeholder_user_id) FROM stakeholder_user_has_companies WHERE stakeholder_user_id = ?1) THEN '1' ELSE '0' END) AS company", nativeQuery = true)
    String existUserCompany(Long stkId);

    /**
     * Find user has company
     */
    StakeholderUserHasCompanyEntity findByStakeholderUserId(Long skyuserId);

    /**
     * Find Staff
     */
    StakeholderUserHasCompanyEntity findByStakeholderUserIdAndStatus(Long skyuserId, Integer status);

    StakeholderUserHasCompanyEntity findByStakeholderUserIdAndStakeholderCompanyId(Long skyuserId, Long companyId);
}
