package com.skybooking.skypointservice.v1_0_0.service.upgradeLevel;

import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigUpgradeLevelEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.upgradeLevel.UpgradeLevelHistoryEntity;
import com.skybooking.skypointservice.v1_0_0.io.repository.upgradeLevel.UpgradeLevelHistoryRP;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpgradeLevelIP implements UpgradeLevelSV {

    @Autowired
    private UpgradeLevelHistoryRP upgradeLevelHistoryRP;

    @Override
    public void save(Integer accountId, ConfigUpgradeLevelEntity configUpgradeLevelEntity) {
        UpgradeLevelHistoryEntity upgradeLevelHistory = upgradeLevelHistoryRP.findLastUpgrade(accountId);

        if (upgradeLevelHistory == null) {
            upgradeLevelHistory = new UpgradeLevelHistoryEntity();
            upgradeLevelHistory.setAccountId(accountId);
            BeanUtils.copyProperties(configUpgradeLevelEntity, upgradeLevelHistory);
            upgradeLevelHistory.setLevelCode(configUpgradeLevelEntity.getCode());
            upgradeLevelHistory.setUpgradeLevelId(upgradeLevelHistory.getId());
            upgradeLevelHistoryRP.save(upgradeLevelHistory);
        } else if (!upgradeLevelHistory.getLevelCode().equals(configUpgradeLevelEntity.getCode())) {
            upgradeLevelHistory = new UpgradeLevelHistoryEntity();
            upgradeLevelHistory.setAccountId(accountId);
            BeanUtils.copyProperties(configUpgradeLevelEntity, upgradeLevelHistory);
            upgradeLevelHistory.setUpgradeLevelId(configUpgradeLevelEntity.getId());
            upgradeLevelHistory.setLevelCode(configUpgradeLevelEntity.getCode());
            upgradeLevelHistoryRP.save(upgradeLevelHistory);
        }
    }
}
