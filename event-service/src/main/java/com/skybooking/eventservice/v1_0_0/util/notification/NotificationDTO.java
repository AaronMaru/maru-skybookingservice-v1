package com.skybooking.eventservice.v1_0_0.util.notification;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NotificationDTO {

    protected Long stakeholderUserId;
    protected Long stakeholderCompanyId;
    protected String transactionCode;
    protected String type;
    protected String script;
    protected BigDecimal amount;
    protected String transactionFor;

}
