package com.skybooking.skypointservice.v1_0_0.service.transaction;

import com.skybooking.skypointservice.v1_0_0.ui.model.request.transaction.TransactionRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface TransactionSV {
    StructureRS getTransactionDetail(TransactionRQ transactionRQ);

    StructureRS getRecentOfflineTopUp();

    StructureRS getOfflineTopUpTransactionDetail(HttpServletRequest httpServletRequest, TransactionRQ transactionRQ);

    StructureRS searchOfflineTransaction(String searchValue);

    StructureRS getOnlineTopUpTransactionDetail(TransactionRQ transactionRQ);
}
