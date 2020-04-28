package com.skybooking.skyflightservice.v1_0_0.io.repository.notification;


import com.skybooking.skyflightservice.v1_0_0.io.entity.notification.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRP extends JpaRepository<NotificationEntity, Long> {

    @Query(value = "SELECT * FROM user_notification_history WHERE id = ?1 AND stakeholder_user_id = ?2 AND (CASE WHEN ?3 THEN stakeholder_company_id = ?3 ELSE stakeholder_company_id IS NULL END)", nativeQuery = true)
    NotificationEntity findNotification(Long id, Long skyuserId, Long companyId);

}
