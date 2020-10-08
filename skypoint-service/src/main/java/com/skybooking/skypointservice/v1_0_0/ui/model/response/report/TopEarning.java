package com.skybooking.skypointservice.v1_0_0.ui.model.response.report;

import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report.TopEarningTO;
import lombok.Data;

@Data
public class TopEarning extends TopEarningTO {
    private String userName;
    private String thumbnail;
}
