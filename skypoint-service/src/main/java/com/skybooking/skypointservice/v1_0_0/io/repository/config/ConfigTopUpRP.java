package com.skybooking.skypointservice.v1_0_0.io.repository.config;

import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigTopUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigTopUpRP extends JpaRepository<ConfigTopUpEntity, Integer> {
    ConfigTopUpEntity findByTypeAndTopUpKeyAndStatus(String type, String topupKey, Boolean status);
}
