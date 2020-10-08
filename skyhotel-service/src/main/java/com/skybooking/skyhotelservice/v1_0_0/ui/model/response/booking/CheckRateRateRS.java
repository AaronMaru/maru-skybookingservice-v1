package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking;

import com.skybooking.skyhotelservice.constant.RateType;
import lombok.Data;

@Data
public class CheckRateRateRS {
    private String key;
    private RateType type;
    private boolean have = false;

    public void setType(RateType type) {
        this.type = type;
        this.have = type.equals(RateType.RECHECK);
    }
}
