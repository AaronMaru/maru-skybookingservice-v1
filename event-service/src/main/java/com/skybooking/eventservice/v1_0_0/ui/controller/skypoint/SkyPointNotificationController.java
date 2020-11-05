package com.skybooking.eventservice.v1_0_0.ui.controller.skypoint;

import com.skybooking.eventservice.v1_0_0.service.notification.NotificationSV;
import com.skybooking.eventservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.eventservice.v1_0_0.ui.model.request.notification.NotificationRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.request.notification.NotificationTopUpRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.request.notification.NotificationUpgradeLevelRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0/notification")
public class SkyPointNotificationController extends BaseController {

    @Autowired
    private NotificationSV notificationSV;

    @PostMapping("no-auth/top-up")
    public ResponseEntity<StructureRS> topUpSkyPoint(@Valid @RequestBody NotificationTopUpRQ notificationTopUpRQ) {

        notificationTopUpRQ.setScript("skp_top_up_notification");
        notificationSV.topUp(notificationTopUpRQ);

        return response(new StructureRS(HttpStatus.OK.value(), "res_succ", null));
    }

    @PostMapping
    public ResponseEntity<StructureRS> sendNotification(@Valid @RequestBody NotificationRQ notificationRQ) {
        notificationSV.sendNotification(notificationRQ);
        return response(new StructureRS(HttpStatus.OK.value(), "res_succ", null));
    }

    @PostMapping("upgrade-level")
    public ResponseEntity<StructureRS> upgradeLevel(@Valid @RequestBody NotificationUpgradeLevelRQ notificationUpgradeLevelRQ) {
        notificationSV.upgradeLevel(notificationUpgradeLevelRQ);
        return response(new StructureRS(HttpStatus.OK.value(), "res_succ", null));
    }

}
