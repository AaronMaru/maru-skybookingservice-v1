package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class FilterInfoCached implements Serializable {
    private BigDecimal price;
    private Integer star;

    public FilterInfoCached(HotelCached hotelCached) {
        this.price = hotelCached.getPriceUnit().getAmount();
        this.star = hotelCached.getBasic().getStar();
    }
}
