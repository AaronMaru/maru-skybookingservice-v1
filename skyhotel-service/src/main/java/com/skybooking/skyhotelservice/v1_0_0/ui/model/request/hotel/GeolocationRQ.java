package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel;

import com.skybooking.skyhotelservice.exception.anotation.IsDecimal;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class GeolocationRQ {
    @NotNull(message = "latitude cannot be null")
    @IsDecimal
    private Double latitude;

    @NotNull(message = "longitude cannot be null")
    @IsDecimal
    private Double longitude;

    @Positive
    private Integer radius = 20;

    public void setRadius(Integer radius) {
        if (radius > 200)
            this.radius = 200;
        else if (radius < 20)
            this.radius = 20;
        else
            this.radius = radius;
    }
}
