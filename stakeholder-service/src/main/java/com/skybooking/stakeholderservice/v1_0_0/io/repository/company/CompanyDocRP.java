package com.skybooking.stakeholderservice.v1_0_0.io.repository.company;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyDocsEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyDocRP extends JpaRepository<StakeholderCompanyDocsEntity, Long> {

    List<StakeholderCompanyDocsEntity> findByStakeholderCompanyAndType(StakeholderCompanyEntity company, String type);


}
