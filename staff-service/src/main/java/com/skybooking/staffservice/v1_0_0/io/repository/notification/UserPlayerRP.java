package com.skybooking.staffservice.v1_0_0.io.repository.notification;

import com.skybooking.staffservice.v1_0_0.io.enitity.notification.UserPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserPlayerRP extends JpaRepository<UserPlayerEntity, Long> {
    List<UserPlayerEntity> findByStakeholderCompanyId(Long companyId);

    @Query(value = "SELECT * FROM user_player WHERE stakeholder_user_id = ?1 LIMIT 1", nativeQuery = true)
    UserPlayerEntity findByStakeholderUserId(Long skyuserId);
}
