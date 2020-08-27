package com.skybooking.eventservice.v1_0_0.ui.controller;

import com.skybooking.eventservice.v1_0_0.service.notification.NotificationSV;
import com.skybooking.eventservice.v1_0_0.ui.model.request.notification.NotificationRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.request.notification.NotificationTopUpRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0.0/notification")
public class NotificationController {

    @Autowired
    private NotificationSV notificationSV;

    @PostMapping("no-auth/top-up")
    public void topUpSkyPoint(@RequestBody NotificationTopUpRQ notificationTopUpRQ) {

        notificationTopUpRQ.setScript("skp_top_up_notification");
        notificationSV.topUp(notificationTopUpRQ);

    }

    @PostMapping
    public void sendNotification(@RequestBody NotificationRQ notificationRQ) {
        notificationSV.sendNotification(notificationRQ);
    }
}
