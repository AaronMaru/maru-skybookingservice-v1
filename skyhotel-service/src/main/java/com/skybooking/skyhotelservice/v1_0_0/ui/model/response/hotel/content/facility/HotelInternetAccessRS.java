package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.facility;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
public class HotelInternetAccessRS extends BaseHotelFacilityRS {

    private boolean isAccessible;
    private boolean feeFlag;
    private BigDecimal amount;

}
