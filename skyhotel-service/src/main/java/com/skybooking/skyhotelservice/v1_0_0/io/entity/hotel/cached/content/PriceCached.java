package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.price.PriceUnitCached;
import lombok.Data;

import java.io.Serializable;

@Data
public class PriceCached implements Serializable {
    private PriceUnitCached priceUnit;
    private DetailCached detail;
}
