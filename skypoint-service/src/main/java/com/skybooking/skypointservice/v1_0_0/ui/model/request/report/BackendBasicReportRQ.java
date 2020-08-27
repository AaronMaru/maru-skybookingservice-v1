package com.skybooking.skypointservice.v1_0_0.ui.model.request.report;

import lombok.Data;

import java.util.Date;

@Data
public class BackendBasicReportRQ {
    private Date startDate;
    private Date endDate;
}
