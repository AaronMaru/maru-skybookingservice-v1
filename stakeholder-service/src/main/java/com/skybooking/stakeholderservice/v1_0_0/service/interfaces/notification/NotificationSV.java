package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.notification;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification.NotificationDetailRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification.NotificationPagingRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification.NotificationRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationSV {

    void removeNF(Long id);
    NotificationDetailRS readable(Long id);
    NotificationPagingRS getNotifications();
    NotificationDetailRS detailNotification(Long id);

}
