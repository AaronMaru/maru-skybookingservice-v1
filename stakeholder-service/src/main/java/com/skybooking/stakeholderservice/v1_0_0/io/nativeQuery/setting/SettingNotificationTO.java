package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.setting;

import lombok.Data;

@Data
public class SettingNotificationTO {

    private Integer stakeholder;
    private Integer company;
    private Boolean emailNotification;
    private Boolean pushNotification;

}
