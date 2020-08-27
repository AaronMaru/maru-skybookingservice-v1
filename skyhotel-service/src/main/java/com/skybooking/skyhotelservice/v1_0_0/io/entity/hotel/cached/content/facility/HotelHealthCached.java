package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content.facility;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
public class HotelHealthCached extends BaseHotelFacilityCached {

    private BigDecimal amount;
    private Integer number;
    private String timeTo;
    private String timeFrom;

    private boolean feeFlag;
    private boolean numberFlag;
    private boolean timeFlag;

}
