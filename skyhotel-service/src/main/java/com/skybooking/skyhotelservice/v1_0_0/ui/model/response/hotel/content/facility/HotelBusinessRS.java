package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.facility;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
public class HotelBusinessRS extends BaseHotelFacilityRS {

    private BigDecimal amount;
    private Integer number;

    private boolean feeFlag;
    private boolean numberFlag;

}
