package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.report;

import lombok.Data;

import java.util.List;

@Data
public class ReportPaginationRS {
    private Integer page;
    private Integer size;
    private Long totals;
    private List<ReportListRS> listings;
}
