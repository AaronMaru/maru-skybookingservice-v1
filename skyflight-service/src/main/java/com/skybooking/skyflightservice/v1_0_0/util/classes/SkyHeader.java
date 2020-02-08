package com.skybooking.skyflightservice.v1_0_0.util.classes;

import lombok.Data;

@Data
public class SkyHeader {

    private String localization = "en";
    private String playerId;
    private String originatingIp;
    private String currencyCode = "USD";
    private String userAgents;
    private String deviceId;
    private String currentTimezone;
    private Integer companyId;
}
