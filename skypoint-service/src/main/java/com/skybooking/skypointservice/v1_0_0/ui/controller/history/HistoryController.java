package com.skybooking.skypointservice.v1_0_0.ui.controller.history;

import com.skybooking.skypointservice.v1_0_0.service.hisotry.HistorySV;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1.0.0/history")
public class HistoryController {
    @Autowired
    private HistorySV historySV;

    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    public StructureRS getTransactionHistoryByAccount(HttpServletRequest httpServletRequest,
                                                      @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
                                                      @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,
                                                      @RequestParam(value = "transactionTypeCode", defaultValue = "all") String transactionTypeCode,
                                                      @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                      @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return historySV.getTransactionHistoryByUserAccount(httpServletRequest, startDate, endDate, transactionTypeCode,
                page, size);
    }

    @RequestMapping(value = "/transaction/detail/{transactionCode}", method = RequestMethod.POST)
    public StructureRS getTransactionHistoryDetail(HttpServletRequest httpServletRequest,
                                                   @PathVariable(name = "transactionCode") String transactionCode) {
        return historySV.getTransactionHistoryDetail(httpServletRequest, transactionCode);
    }

    @RequestMapping(value = "/sky-owner/transaction", method = RequestMethod.GET)
    public StructureRS skyOwnerTransactionHistory(HttpServletRequest httpServletRequest,
                                                  @RequestParam(value = "stakeholderUserId") Integer stakeholderUserId,
                                                  @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                  @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return historySV.skyOwnerTransactionHistory(httpServletRequest, stakeholderUserId, page, size);
    }

    @RequestMapping(value = "download/receipt/top-up", method = RequestMethod.GET)
    public StructureRS earningCheck(HttpServletRequest httpServletRequest, @RequestParam(name = "code") String code) {
        return historySV.downloadReceipt(httpServletRequest, code);
    }

    @RequestMapping(value = "/transaction/export", method = RequestMethod.GET)
    public StructureRS exportTransactionHistoryByUserAccount(HttpServletRequest httpServletRequest,
                                                             @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
                                                             @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate) {
        return historySV.exportTransactionHistoryByUserAccount(httpServletRequest, startDate, endDate);
    }
}
