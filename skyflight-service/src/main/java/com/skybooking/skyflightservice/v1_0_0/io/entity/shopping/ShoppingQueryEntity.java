package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class ShoppingQueryEntity implements Serializable {

    private String id = UUID.randomUUID().toString();
    private FlightShopping query;

    public ShoppingQueryEntity(FlightShopping query) {
        this.query = query;
    }


}
