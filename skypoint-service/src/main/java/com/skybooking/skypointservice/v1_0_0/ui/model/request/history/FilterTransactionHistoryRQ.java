package com.skybooking.skypointservice.v1_0_0.ui.model.request.history;

import lombok.Data;

import java.util.Date;

@Data
public class FilterTransactionHistoryRQ {
    private Date startDate;
    private Date endDate;
}
