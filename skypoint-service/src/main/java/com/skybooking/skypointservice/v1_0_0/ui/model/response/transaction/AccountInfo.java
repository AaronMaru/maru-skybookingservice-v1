package com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction;

import lombok.Data;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Data
public class AccountInfo {
    protected String levelCode = "LEVEL1";
    protected String levelName;
    protected BigDecimal earning = BigDecimal.valueOf(0);
    protected BigDecimal earningExtra = BigDecimal.valueOf(0);
    protected BigDecimal savedPoint = BigDecimal.valueOf(0);
    protected BigDecimal balance = BigDecimal.valueOf(0);
}
