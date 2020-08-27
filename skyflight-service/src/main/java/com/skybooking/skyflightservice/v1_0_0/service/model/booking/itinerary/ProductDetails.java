package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDetails implements Serializable {

    @JsonProperty("ProductName")
    private ProductName productName;
    @JsonProperty("Air")
    private Air air;
    private String productCategory;

}
