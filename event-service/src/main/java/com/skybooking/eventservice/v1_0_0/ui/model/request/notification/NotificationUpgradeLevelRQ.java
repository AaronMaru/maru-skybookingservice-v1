package com.skybooking.eventservice.v1_0_0.ui.model.request.notification;

import com.skybooking.eventservice.v1_0_0.util.notification.NotificationDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class NotificationUpgradeLevelRQ extends NotificationDTO {

    @NotNull
    private String level;

}
