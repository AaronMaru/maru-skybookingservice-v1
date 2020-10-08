package com.skybooking.skypointservice.v1_0_0.ui.model.response.report;

import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report.SpentPointDetailReportTO;
import lombok.Data;

import java.util.List;

@Data
public class SpentPointDetailReportRS {
    private List<SpentPointDetailReportTO> spentPointDetailReportList;
}
