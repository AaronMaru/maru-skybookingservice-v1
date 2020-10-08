package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CancellationPolicy {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date fromDate;
    private Integer totalNight;
    private BigDecimal percentage;
    private BigDecimal amount;
}
