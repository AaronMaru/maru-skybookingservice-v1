package com.skybooking.stakeholderservice.v1_0_0.io.repository.company;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.BussinessTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BussinessTypeRP extends JpaRepository<BussinessTypeEntity, Long> {
    List<BussinessTypeEntity> findAllByStatusAndDeletedAtIsNull(Integer status);
}
