package com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp;

import com.skybooking.skypointservice.v1_0_0.ui.model.request.ContactInfo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OnlineTopUpRQ {
    private BigDecimal amount;
    private String paymentMethodCode;
    private String remark;
    private ContactInfo contactInfo;
}
