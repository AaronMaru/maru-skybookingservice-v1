package com.skybooking.skypointservice.v1_0_0.io.repository.upgradeLevel;

import com.skybooking.skypointservice.v1_0_0.io.entity.upgradeLevel.UpgradeLevelHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UpgradeLevelHistoryRP extends JpaRepository<UpgradeLevelHistoryEntity, Integer> {
    UpgradeLevelHistoryEntity findTopByAccountIdOrderByIdDesc(Integer accountId);

    @Query(value = "SELECT * FROM upgrade_level_history where account_id = :accountId ORDER BY id desc LIMIT 1", nativeQuery = true)
    UpgradeLevelHistoryEntity findLastUpgrade(Integer accountId);
}
