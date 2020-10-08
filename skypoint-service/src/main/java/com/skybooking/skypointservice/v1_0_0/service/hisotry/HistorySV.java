package com.skybooking.skypointservice.v1_0_0.service.hisotry;

import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public interface HistorySV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Fronted: Get transactions history
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param startDate          String
     * @param endDate            String
     * @param transactionTypCode String
     * @return StructureRS
     */
    StructureRS getTransactionHistoryByUserAccount(HttpServletRequest httpServletRequest,
                                                   String startDate, String endDate, String transactionTypCode,
                                                   Integer page, Integer limit);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Frontend: Get transaction detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param transactionCode    String
     * @return StructureRS
     */
    StructureRS getTransactionHistoryDetail(HttpServletRequest httpServletRequest, String transactionCode);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Fronted: Get transactions history
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param page               Integer
     * @param limit              Integer
     * @return StructureRS
     */
    StructureRS skyOwnerTransactionHistory(HttpServletRequest httpServletRequest, Integer page, Integer limit);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Frontend: Download receipt topUp pdf
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param code               String
     * @return StructureRS
     */
    StructureRS downloadReceipt(HttpServletRequest httpServletRequest, String code);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Fronted: Get transactions history
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param startDate          String
     * @param endDate            String
     * @return StructureRS
     */
    StructureRS exportTransactionHistoryByUserAccount(HttpServletRequest httpServletRequest,
                                                      String startDate, String endDate);
}
