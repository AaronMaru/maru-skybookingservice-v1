package com.skybooking.stakeholderservice.v1_0_0.io.repository.notification;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.notification.OneSignalPushNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneSignalPushNotificationRP extends JpaRepository<OneSignalPushNotification, Long> {
}
