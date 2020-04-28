package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class PriceDetailRS implements Serializable {

    private String id;
    private PriceRS price;
    private PriceRS basePrice;
    private boolean commission;
    private List<PriceListRS> details = new ArrayList<>();
}
