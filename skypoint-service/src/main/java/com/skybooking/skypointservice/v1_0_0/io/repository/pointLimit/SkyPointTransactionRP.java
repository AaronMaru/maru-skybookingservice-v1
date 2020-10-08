package com.skybooking.skypointservice.v1_0_0.io.repository.pointLimit;

import com.skybooking.skypointservice.v1_0_0.io.entity.pointLimit.SkyPointTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkyPointTransactionRP extends JpaRepository<SkyPointTransactionEntity, Integer> {
}
