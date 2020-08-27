package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.location;

import com.skybooking.core.validators.annotations.IsIn;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class GeolocationRQ {
    @NotNull(message = "Latitude must not be null")
    private BigDecimal latitude;
    @NotNull(message = "Longitude must not be null")
    private BigDecimal longitude;
    private double secondaryLatitude;
    private double secondaryLongitude;
    @Positive
    @Max(200)
    private int radius = 20;
    @IsIn(contains = {"km"})
    private String unit = "km";
}
