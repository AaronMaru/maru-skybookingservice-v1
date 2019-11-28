package com.skybooking.stakeholderservice.v1_0_0.io.repository.company;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRP extends JpaRepository<StakeholderCompanyEntity, Long> {

    @Query(value = "SELECT * FROM stakeholder_companies WHERE company_name = ?1 AND (CASE WHEN ?2 THEN id NOT IN (?2) ELSE id IS NOT NULL END) LIMIT 1", nativeQuery = true)
    StakeholderCompanyEntity findFirstByCompanyName(String name, Long id);

    @Query(value = "SELECT * FROM stakeholder_companies WHERE company_code LIKE %?1% ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    StakeholderCompanyEntity findByCompanyCode(String companyCode);

}
