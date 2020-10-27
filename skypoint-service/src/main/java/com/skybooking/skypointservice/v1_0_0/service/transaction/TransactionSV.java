package com.skybooking.skypointservice.v1_0_0.service.transaction;

import com.skybooking.skypointservice.v1_0_0.ui.model.request.transaction.TransactionRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface TransactionSV {
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Common: Call by payment-service to get online top up transaction detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param transactionRQ TransactionRQ
     * @return StructureRS
     */
    StructureRS getTransactionDetail(TransactionRQ transactionRQ);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Get all recent offline top up transactions (within a day, 5 last records)
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return StructureRS
     */
    StructureRS getRecentTopUp();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Get recent transactions by pagination and userCode
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param userCode           String
     * @param size               Integer
     * @param page               Integer
     * @return StructureRS
     */
    StructureRS getRecentTransaction(HttpServletRequest httpServletRequest, String userCode, Integer size, Integer page);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Get offline top up transaction detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param transactionCode    String
     * @return StructureRS
     */
    StructureRS getOfflineTopUpTransactionDetail(HttpServletRequest httpServletRequest, String transactionCode);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Search offline transaction
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param searchValue String
     * @return StructureRS
     */
    StructureRS searchOfflineTransaction(String searchValue);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Fronted: Get online top up transaction detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param transactionCode String
     * @return StructureRS
     */
    StructureRS getOnlineTopUpTransactionDetail(String transactionCode);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Get pending offline top up list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param page Integer
     * @param size Integer
     * @return StructureRS
     */
    StructureRS getPendingOfflineTopUpList(Integer page, Integer size);
}
