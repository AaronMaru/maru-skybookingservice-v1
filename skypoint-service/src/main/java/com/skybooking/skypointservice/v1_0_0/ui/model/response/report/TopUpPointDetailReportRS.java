package com.skybooking.skypointservice.v1_0_0.ui.model.response.report;

import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report.TopUpPointDetailReportTO;
import lombok.Data;

import java.util.List;

@Data
public class TopUpPointDetailReportRS {
    private List<TopUpPointDetailReportTO> topUpPointDetailReportList;
}
