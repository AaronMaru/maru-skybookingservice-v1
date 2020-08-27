package com.skybooking.eventservice.v1_0_0.service.notification;

import com.skybooking.eventservice.v1_0_0.ui.model.request.notification.NotificationRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.request.notification.NotificationTopUpRQ;
import org.springframework.stereotype.Service;

@Service
public interface NotificationSV {

    void topUp(NotificationTopUpRQ notificationTopUpRQ);

    void sendNotification(NotificationRQ notificationDTO);

}
