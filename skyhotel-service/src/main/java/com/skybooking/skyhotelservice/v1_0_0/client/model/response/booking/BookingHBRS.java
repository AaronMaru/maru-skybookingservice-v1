package com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking;

import com.skybooking.skyhotelservice.constant.BookingStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
public class BookingHBRS {
    private String code;
    private String reference;
    private String clientReference;
    private String creationDate;
    private BookingStatus status;
    private BookingModificationPoliciesHBRS modificationPolicies;
    private String creationUser;
    private BookingHolderHBRS holder;
    private BookingHotelHBRS hotel;
    private BookingInvoiceCompanyHBRS invoiceCompany;
    private BigDecimal totalNet = BigDecimal.ZERO;
    private BigDecimal pendingAmount = BigDecimal.ZERO;
    private String currency;
}
