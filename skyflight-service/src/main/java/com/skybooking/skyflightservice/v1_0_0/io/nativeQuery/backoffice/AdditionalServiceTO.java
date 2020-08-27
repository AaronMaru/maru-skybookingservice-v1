package com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.backoffice;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AdditionalServiceTO {

    private Integer id;
    private Integer bookingId;
    private String bookingCode;
    private Date serviceHappenedAt;
    private String serviceType;
    private String serviceDescription;
    private String supplierName;
    private BigDecimal supplierFee;
    private String supplierDescription;
    private BigDecimal customerFee;
    private String customerDescription;
    private Date bankReceivedDate;
    private BigDecimal bankFee;
    private String bankDescription;
    private Boolean status;
    private Integer createdBy;
    private Integer updatedBy;
    private Date createdAt;
    private Date updatedAt;
}
