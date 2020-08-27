package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content.facility;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
public class HotelInternetAccessCached extends BaseHotelFacilityCached {

    private boolean isAccessible;
    private boolean feeFlag;
    private BigDecimal amount;

}
