package com.skybooking.skypointservice.v1_0_0.io.repository.config;

import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigUpgradeLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ConfigUpgradeLevelRP extends JpaRepository<ConfigUpgradeLevelEntity, Integer> {

    @Query(value = "SELECT * FROM config_upgrade_levels WHERE `from_value` <= :value AND `to_value` > :value AND status = true AND `type` = :userType LIMIT 1", nativeQuery = true)
    ConfigUpgradeLevelEntity getRecordByFromValueAndTypeAndToValue(BigDecimal value, String userType);

    @Query(value = "SELECT * FROM config_upgrade_levels WHERE `from_value` <= :value AND `to_value` > :value AND status = true  AND language_code = :languageCode" +
            " AND `type` = :userType LIMIT 1", nativeQuery = true)
    ConfigUpgradeLevelEntity getRecordByFromValueAndToValueAndTypeAndLanguageCode(BigDecimal value, String userType, String languageCode);

    ConfigUpgradeLevelEntity findByCodeAndTypeAndLanguageCode(String code, String userType, String languageCode);
}
