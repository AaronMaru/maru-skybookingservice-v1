package com.skybooking.skyflightservice.v1_0_0.ui.model.response.backoffice.additionalservice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AdditionalServiceRS {
    private Integer id;
    private Integer bookingId;
    private Integer createdBy;
    private Integer updatedBy;
    private ServiceRS service;
    private SupplierRS supplier;
    private CustomerRS customer;
    private BankRS bank;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
}
