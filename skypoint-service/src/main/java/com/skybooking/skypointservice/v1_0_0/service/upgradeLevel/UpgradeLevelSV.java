package com.skybooking.skypointservice.v1_0_0.service.upgradeLevel;

import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigUpgradeLevelEntity;
import org.springframework.stereotype.Service;

@Service
public interface UpgradeLevelSV {

    void save(Integer accountId, ConfigUpgradeLevelEntity configUpgradeLevelEntity);

}
