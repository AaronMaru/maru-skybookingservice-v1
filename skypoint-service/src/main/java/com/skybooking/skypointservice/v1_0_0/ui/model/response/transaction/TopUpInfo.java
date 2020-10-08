package com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class TopUpInfo {
    private Integer id;
    private String transactionCode;
    private BigDecimal amount;
    private String referenceCode;
    private String remark;
    private List<String> files;
    private String createdBy;
    private Date createdAt;
}
