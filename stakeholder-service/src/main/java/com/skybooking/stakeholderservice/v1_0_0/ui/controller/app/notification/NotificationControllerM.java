package com.skybooking.stakeholderservice.v1_0_0.ui.controller.app.notification;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.notification.NotificationSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/mv1.0.0/notification")
public class NotificationControllerM {

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
    public  ResRS getNotifiStaff() {
        return localization.resAPI(HttpStatus.OK, "res_succ", notificationSV.getNotifications("staff"));
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
    public ResRS getNotifiSkyuser() {
        return localization.resAPI(HttpStatus.OK, "res_succ", notificationSV.getNotifications("skyuser"));
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Details notification
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResRS detailsNotifi(@PathVariable Long id) {
        return localization.resAPI(HttpStatus.OK, "res_succ", notificationSV.detailNotification(id));
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
    public ResRS removeNF(@PathVariable Long id) {
        notificationSV.removeNF(id);
        return localization.resAPI(HttpStatus.OK,"del_succ", "");
    }

}
