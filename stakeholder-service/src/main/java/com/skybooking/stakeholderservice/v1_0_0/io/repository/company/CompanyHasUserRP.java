package com.skybooking.stakeholderservice.v1_0_0.io.repository.company;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderUserHasCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyHasUserRP extends JpaRepository<StakeholderUserHasCompanyEntity, Long> {

    @Query(value = "SELECT (CASE WHEN (SELECT COUNT(stakeholder_user_id) FROM stakeholder_user_has_companies WHERE stakeholder_user_id = ?1) THEN '1' ELSE '0' END) AS company", nativeQuery = true)
    String existUserCompany(Long stkId);

    StakeholderUserHasCompanyEntity findByStakeholderUserId(Long skyuserId);

    StakeholderUserHasCompanyEntity findByStakeholderUserIdAndStatus(Long skyuserId, Integer status);

    StakeholderUserHasCompanyEntity findByStakeholderCompanyIdAndStatus(Long companyId, Integer status);

}
