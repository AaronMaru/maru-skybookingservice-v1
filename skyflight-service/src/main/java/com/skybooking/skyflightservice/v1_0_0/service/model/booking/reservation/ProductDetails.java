package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDetails implements Serializable {
    @JsonProperty("Air")
    public ProductAir air;
    @JsonProperty("ProductName")
    public ProductName productName;
    public String productCategory;
}
