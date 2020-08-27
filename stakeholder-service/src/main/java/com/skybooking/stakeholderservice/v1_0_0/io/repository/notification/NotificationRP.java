package com.skybooking.stakeholderservice.v1_0_0.io.repository.notification;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.notification.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRP extends JpaRepository<NotificationEntity, Long> {
    NotificationEntity findByIdAndStakeholderUserId(Long id, Long skyuserId);
    NotificationEntity findByIdAndStakeholderCompanyId(Long id, Long companyId);

    @Query(value = "SELECT * FROM user_notification_history WHERE stakeholder_user_id = ?1 AND (CASE WHEN ?2 IS NOT NULL THEN stakeholder_company_id = ?2 ELSE stakeholder_company_id IS NULL END) AND readable IS NULL", nativeQuery = true)
    List<NotificationEntity> findBySkyuserIdAndCompanyId(Long skyuserId, Long companyId);

    @Query(value = "SELECT COUNT(id) FROM user_notification_history WHERE stakeholder_user_id = ?1 AND (CASE WHEN ?2 IS NOT NULL THEN stakeholder_company_id = ?2 ELSE stakeholder_company_id IS NULL END) AND readable IS NULL", nativeQuery = true)
    String countAllBy(Long skyuserId, Long companyId);
}
