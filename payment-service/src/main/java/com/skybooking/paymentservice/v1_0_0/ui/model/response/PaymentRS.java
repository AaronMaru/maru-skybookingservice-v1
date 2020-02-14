package com.skybooking.paymentservice.v1_0_0.ui.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentRS {

    private Long id;
    private String token;
    private String bookingCode;
    private Integer render;
    private String callbackUrl;
    private String platform;
    private Date expiredAt;
    private String paymentCode;
    private String provider;
    private BigDecimal amount;
    private String description;
    private String playerPhone;
    private Integer paymentId;
    private String userName;
    private String userEmail;
    private String userContact;
    private String remark;

}
