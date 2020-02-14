package com.skybooking.paymentservice.v1_0_0.ui.model.request;

import lombok.Data;

@Data
public class IPay88RQ {

    private String merchantCode;
    private Integer paymentId;
    private String refNo;
    private Double amount;
    private String currency;
    private String remark;
    private String transId;
    private String authCode;
    private String status;
    private String errDesc;
    private String signature;

}
