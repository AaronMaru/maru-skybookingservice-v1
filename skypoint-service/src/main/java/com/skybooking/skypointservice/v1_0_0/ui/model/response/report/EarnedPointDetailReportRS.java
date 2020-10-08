package com.skybooking.skypointservice.v1_0_0.ui.model.response.report;

import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report.EarnedPointDetailReportTO;
import lombok.Data;

import java.util.List;

@Data
public class EarnedPointDetailReportRS {
    private List<EarnedPointDetailReportTO> earnedPointDetailReportList;
}
