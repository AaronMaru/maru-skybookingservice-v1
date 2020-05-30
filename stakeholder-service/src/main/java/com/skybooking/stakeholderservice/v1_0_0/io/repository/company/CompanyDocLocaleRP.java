package com.skybooking.stakeholderservice.v1_0_0.io.repository.company;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyDocsEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyDocsLocaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CompanyDocLocaleRP extends JpaRepository<StakeholderCompanyDocsLocaleEntity, Long> {

    @Transactional
    Long deleteAllByCompanyDocs(StakeholderCompanyDocsEntity companyDoc);

    @Query(value = "SELECT * FROM stakeholder_company_docs_locale WHERE stcompany_doc_id = ?1 AND locale_id = ?2 LIMIT 1", nativeQuery = true)
    StakeholderCompanyDocsLocaleEntity findByCompanyDocsAndLocaleId(StakeholderCompanyDocsEntity companyDoc, Long localeId);

}
