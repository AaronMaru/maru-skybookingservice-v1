package com.skybooking.skypointservice.v1_0_0.io.repository.skyPointManagement;

import com.skybooking.skypointservice.v1_0_0.io.entity.skyPointManagement.SkyPointTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkyPointTransactionRP extends JpaRepository<SkyPointTransactionEntity, Integer> {
}
