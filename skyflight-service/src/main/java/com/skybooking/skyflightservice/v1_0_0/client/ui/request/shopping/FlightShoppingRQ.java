package com.skybooking.skyflightservice.v1_0_0.client.ui.request.shopping;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skybooking.skyflightservice.v1_0_0.validator.FlightShopping;
import com.skybooking.skyflightservice.v1_0_0.validator.Include;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Data
@FlightShopping()
public class FlightShoppingRQ {

    @Positive(message = "The number of adult passenger is required.")
    private int adult;

    @PositiveOrZero(message = "The number of child passenger is required.")
    private int child;

    @PositiveOrZero(message = "The number of infant passenger is required.")
    private int infant;

    @NotBlank(message = "The trip type is required.")
    @Include(contains = "one|round|multiple", delimiter = "\\|")
    @JsonProperty("trip")
    private String tripType;

    @NotBlank(message = "The cabin class type is required.")
    @Include(contains = "C|F|Y", delimiter = "\\|")
    @JsonProperty("class")
    private String classType;

    @NotNull(message = "The flight shopping information is required.")
    @Size(min = 1)
    @Valid
    private List<FlightLegRQ> legs = new ArrayList<>();

}
