package com.skybooking.skyhistoryservice.v1_0_0.io.repository.company;

import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.company.StakeholderCompanyDocsEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDocsRP extends JpaRepository<StakeholderCompanyDocsEntity, Long> {

    @Query(value = "SELECT * FROM stakeholder_company_docs WHERE stcompany_id = :companyId AND type = 'itenery'", nativeQuery = true)
    StakeholderCompanyDocsEntity getItineraryProfile(Integer companyId);
}
