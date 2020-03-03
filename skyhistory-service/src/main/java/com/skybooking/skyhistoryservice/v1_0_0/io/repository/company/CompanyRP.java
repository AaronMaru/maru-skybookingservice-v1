package com.skybooking.skyhistoryservice.v1_0_0.io.repository.company;

import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRP extends JpaRepository<StakeholderCompanyEntity, Long> {

}
