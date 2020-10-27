package com.skybooking.eventservice.v1_0_0.util.notification;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NotificationDTO {

    private Long stakeholderUserId;
    private Long stakeholderCompanyId;
    private String transactionCode;
    private String type;
    private String script;
    private BigDecimal amount;

}
