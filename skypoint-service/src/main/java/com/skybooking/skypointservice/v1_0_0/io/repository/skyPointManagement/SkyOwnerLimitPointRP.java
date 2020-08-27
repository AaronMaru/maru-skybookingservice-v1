package com.skybooking.skypointservice.v1_0_0.io.repository.skyPointManagement;

import com.skybooking.skypointservice.v1_0_0.io.entity.skyPointManagement.SkyOwnerLimitPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkyOwnerLimitPointRP extends JpaRepository<SkyOwnerLimitPointEntity, Integer> {
    SkyOwnerLimitPointEntity findByStakeholderUserIdAndStatus(Integer stakeholderUserId, Boolean status);
}
