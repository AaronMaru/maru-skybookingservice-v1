package com.skybooking.skypointservice.v1_0_0.ui.model.response.report;

import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report.TopBalanceTO;
import lombok.Data;

@Data
public class TopBalance extends TopBalanceTO {
    private String userName;
    private String thumbnail;
}
