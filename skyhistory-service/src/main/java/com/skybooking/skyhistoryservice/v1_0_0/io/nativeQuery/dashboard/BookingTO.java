package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class BookingTO {

    private Integer id;
    private Integer userId;
    private Integer companyId;
    private String slug;
    private String bookingCode;
    private String bookingType;
    private String itineraryRef;
    private String tripType;

    private String custName;
    private String custAddress;
    private String custCity;
    private String custZip;

    private String contLocationCode;
    private String contPhone;
    private String contEmail;
    private String contFullname;
    private String contPhonecode;

    private Timestamp depDate;
    private Timestamp localIssueDate;

    private String currencyConvert;
    private BigDecimal currencyConRate;
    private String markupPercentage;
    private BigDecimal totalAmount;
    private BigDecimal markupAmount;
    private String remark;
    private Byte status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer pq;
    private String currencyCode;
    private Double vatPercentage;
    private Boolean isCheck;
    private String itineraryFile;
    private String receiptFile;
    private String itineraryPath;
    private String receiptPath;
    private BigDecimal disPayMetPercentage;
    private BigDecimal disPayMetAmount;
    private BigDecimal markupPayPercentage;
    private BigDecimal markupPayAmount;
    private BigDecimal payMetFeePercentage;
    private BigDecimal payMetFeeAmount;
    private String seen;
    private String description;
}
