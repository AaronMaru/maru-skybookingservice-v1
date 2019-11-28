package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.notification;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.notification.NotificationSV;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wv1.0.0/notification")
public class NotificationControllerW {

    @Autowired
    private NotificationSV notificationSV;

    @Autowired
    private Localization localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get list notification
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/staff")
    public  ResponseEntity getNotifiStaff() {
        return new ResponseEntity<>(notificationSV.getNotifications("staff"), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Remove notification
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param profileRequest
     * @Return ResponseEntity
     */
    @GetMapping("/skyuser")
    public  ResponseEntity getNotifiSkyuser() {
        return new ResponseEntity<>(notificationSV.getNotifications("skyuser"), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Details notification
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/{id}")
    public  ResponseEntity detailsNotifi(@PathVariable Long id) {
        return new ResponseEntity<>(notificationSV.detailNotification(id), HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Remove notification
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param profileRequest
     * @Return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity removeNF(@PathVariable Long id) {
        notificationSV.removeNF(id);
        return new ResponseEntity<>(localization.resAPI("del_succ", ""), HttpStatus.OK);
    }

}
