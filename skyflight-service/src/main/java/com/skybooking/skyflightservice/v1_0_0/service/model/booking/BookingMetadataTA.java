package com.skybooking.skyflightservice.v1_0_0.service.model.booking;

import com.skybooking.skyflightservice.v1_0_0.service.model.security.UserAuthenticationMetaTA;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingMetadataTA {

    private UserAuthenticationMetaTA user;

    private String tripType;
    private String classType;
    private String departureDate;
    private String arrivalDate;

    private double totalAmount;
    private double markupAmount;
    private double markupPercentage;
    private String currencyCode = "USD";
    private String currencyLocaleCode = "USD";
    private BigDecimal exchangeRate = BigDecimal.valueOf(1.00000);

    private boolean cached;

}
