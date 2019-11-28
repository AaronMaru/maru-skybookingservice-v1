package com.skybooking.stakeholderservice.v1_0_0.io.repository.notification;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.notification.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRP extends JpaRepository<NotificationEntity, Long> {

}
