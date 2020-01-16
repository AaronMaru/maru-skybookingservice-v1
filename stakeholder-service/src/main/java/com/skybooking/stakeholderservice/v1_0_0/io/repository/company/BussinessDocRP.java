package com.skybooking.stakeholderservice.v1_0_0.io.repository.company;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.BussinessDocEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BussinessDocRP extends JpaRepository<BussinessDocEntity, Long> {
    BussinessDocEntity findFirstByName(String name);
}
