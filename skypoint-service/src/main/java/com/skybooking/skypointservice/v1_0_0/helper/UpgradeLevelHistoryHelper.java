package com.skybooking.skypointservice.v1_0_0.helper;

import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigUpgradeLevelEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.upgradeLevel.UpgradeLevelHistoryEntity;
import com.skybooking.skypointservice.v1_0_0.io.repository.upgradeLevel.UpgradeLevelHistoryRP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UpgradeLevelHistoryHelper {
    @Autowired
    private UpgradeLevelHistoryRP upgradeLevelHistoryRP;

    public void saveOrUpdateUpgradeLevelHistory(UpgradeLevelHistoryEntity upgradeLevelHistory,
                                                ConfigUpgradeLevelEntity configUpgradeLevel,
                                                Integer accountId, Date cratedAt) {
        upgradeLevelHistory.setAccountId(accountId);
        upgradeLevelHistory.setUpgradeLevelId(configUpgradeLevel.getId());
        upgradeLevelHistory.setRate(configUpgradeLevel.getRate());
        upgradeLevelHistory.setFromValue(configUpgradeLevel.getFromValue());
        upgradeLevelHistory.setToValue(configUpgradeLevel.getToValue());
        upgradeLevelHistory.setLevelCode(configUpgradeLevel.getCode());
        upgradeLevelHistory.setCreatedAt(cratedAt);
        upgradeLevelHistory.setUpdatedAt(new Date());
        upgradeLevelHistoryRP.save(upgradeLevelHistory);
    }
}
