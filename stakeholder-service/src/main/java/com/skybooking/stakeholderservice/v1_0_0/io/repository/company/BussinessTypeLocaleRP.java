package com.skybooking.stakeholderservice.v1_0_0.io.repository.company;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.BussinessTypeLocaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BussinessTypeLocaleRP extends JpaRepository<BussinessTypeLocaleEntity, Long> {

    @Query(value = "SELECT * FROM business_type_locale WHERE business_type_id = ?1 AND locale_id = ?2 LIMIT 1", nativeQuery = true)
    BussinessTypeLocaleEntity findByBusinessTypeIdAndLocaleId(Long bzId, Long localeId);

}
