package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.content.facility;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
public class HotelHealthRSDS extends BaseHotelFacilityRSDS {

    private BigDecimal amount;
    private Integer number;
    private String timeTo;
    private String timeFrom;

    private boolean feeFlag;
    private boolean numberFlag;
    private boolean timeFlag;

}
