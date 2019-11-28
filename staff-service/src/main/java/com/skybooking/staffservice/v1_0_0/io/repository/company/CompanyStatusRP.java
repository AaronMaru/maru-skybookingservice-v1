package com.skybooking.staffservice.v1_0_0.io.repository.company;

import com.skybooking.staffservice.v1_0_0.io.enitity.company.StakeholderCompanyStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyStatusRP extends JpaRepository<StakeholderCompanyStatusEntity, Long> {
}
