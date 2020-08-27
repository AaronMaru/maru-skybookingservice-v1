package com.skybooking.skypointservice.v1_0_0.ui.model.response.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopBalance {
    private String userName;
    private String levelName;
    private BigDecimal balance;
}
