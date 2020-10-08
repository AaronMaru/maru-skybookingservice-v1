package com.skybooking.skypointservice.v1_0_0.io.repository.pointLimit;

import com.skybooking.skypointservice.v1_0_0.io.entity.pointLimit.SkyOwnerLimitPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkyOwnerLimitPointRP extends JpaRepository<SkyOwnerLimitPointEntity, Integer> {
    SkyOwnerLimitPointEntity findByStakeholderUserIdAndStatus(Integer stakeholderUserId, Boolean status);

    SkyOwnerLimitPointEntity findByIdAndStatus(Integer id, Boolean status);

    List<SkyOwnerLimitPointEntity> findAllByStatus(Boolean status);
}
