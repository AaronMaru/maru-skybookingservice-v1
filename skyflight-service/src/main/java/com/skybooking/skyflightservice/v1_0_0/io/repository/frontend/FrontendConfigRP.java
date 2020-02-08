package com.skybooking.skyflightservice.v1_0_0.io.repository.frontend;

import com.skybooking.skyflightservice.v1_0_0.io.entity.frontend.FrontendConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface FrontendConfigRP extends JpaRepository<FrontendConfigEntity, Integer> {

    @Query(value = "SELECT numeric_value FROM frontend_config WHERE name = 'vat_percentage'", nativeQuery = true)
    BigDecimal getVatPercentage();
}
