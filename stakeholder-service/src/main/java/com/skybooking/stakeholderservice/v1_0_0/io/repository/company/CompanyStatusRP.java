package com.skybooking.stakeholderservice.v1_0_0.io.repository.company;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyStatusRP extends JpaRepository<StakeholderCompanyStatusEntity, Long> {

    @Query(value = "SELECT * FROM stakeholder_company_status WHERE company_id = ?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
    StakeholderCompanyStatusEntity findByCompany(Long id);

}
