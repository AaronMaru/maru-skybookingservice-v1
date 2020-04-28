package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class PriceDetail implements Serializable {

    private String id;
    private Price price;
    private Price basePrice;
    private boolean commission;
    private BigDecimal totalCommissionAmount = BigDecimal.ZERO;
    private List<PriceList> details = new ArrayList<>();

}
