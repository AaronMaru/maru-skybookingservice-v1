package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content.facility;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
public class HotelPolicyCached extends BaseHotelFacilityCached {

    private String dateFrom;
    private String dateTo;

    private Integer ageFrom;
    private Integer ageTO;

    private Integer number;
    private BigDecimal amount;

    private boolean ageFlag;
    private boolean dateFlag;
    private boolean numberFlag;
    private boolean feeFlag;
    private boolean amountFlag;

}
