package com.skybooking.skypointservice.v1_0_0.service.hisotry;

import com.skybooking.skypointservice.v1_0_0.ui.model.request.history.FilterTransactionHistoryRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface HistorySV {
    StructureRS getTransactionHistoryByUserAccount();

    StructureRS filterTransactionHistoryByAccount(FilterTransactionHistoryRQ filterTransactionHistoryRQ);

    StructureRS getTransactionHistoryDetail(Integer transactionValueId);
}
