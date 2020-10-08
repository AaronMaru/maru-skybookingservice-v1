package com.skybooking.skypointservice.v1_0_0.ui.controller.transaction;

import com.skybooking.skypointservice.v1_0_0.service.transaction.TransactionSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.transaction.TransactionRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1.0.0/transaction")
public class TransactionController {
    @Autowired
    private TransactionSV transactionSV;

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public StructureRS getTransactionDetail(@RequestBody TransactionRQ transactionRQ) {
        return transactionSV.getTransactionDetail(transactionRQ);
    }

    @PreAuthorize("#oauth2.hasScope('get-recent-offline-topup-skypoint')")
    @RequestMapping(value = "/recent/offline/top-up", method = RequestMethod.GET)
    public StructureRS getRecentOfflineTopUp() {
        return transactionSV.getRecentOfflineTopUp();
    }

    @PreAuthorize("#oauth2.hasScope('get-recent-transaction-skypoint')")
    @RequestMapping(value = "/recent", method = RequestMethod.GET)
    public StructureRS getRecentTransaction(HttpServletRequest httpServletRequest,
                                            @RequestParam String userCode,
                                            @RequestParam(name = "limit", defaultValue = "0") Integer limit,
                                            @RequestParam(name = "page", defaultValue = "1") Integer page) {
        return transactionSV.getRecentTransaction(httpServletRequest, userCode, limit, page);
    }

    @PreAuthorize("#oauth2.hasScope('get-offline-topup-detail-skypoint')")
    @RequestMapping(value = "/offline/top-up/detail/{transactionCode}", method = RequestMethod.POST)
    public StructureRS getOfflineTopUpTransactionDetail(HttpServletRequest httpServletRequest,
                                                        @PathVariable(name = "transactionCode") String transactionCode) {
        return transactionSV.getOfflineTopUpTransactionDetail(httpServletRequest, transactionCode);
    }

    @PreAuthorize("#oauth2.hasScope('search-offline-topup-skypoint')")
    @RequestMapping(value = "/offline/top-up/search", method = RequestMethod.GET)
    public StructureRS searchOfflineTopUpTransaction(@RequestParam String searchValue) {
        return transactionSV.searchOfflineTransaction(searchValue);
    }

    @RequestMapping(value = "/online/top-up/detail/{transactionCode}", method = RequestMethod.POST)
    public StructureRS getTopUpOnlineTransactionDetail(@PathVariable(name = "transactionCode") String transactionCode) {
        return transactionSV.getOnlineTopUpTransactionDetail(transactionCode);
    }

    @RequestMapping(value = "/pending/offline/top-up", method = RequestMethod.GET)
    public StructureRS getPendingOfflineTopUpTransaction(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                                         @RequestParam(name = "limit", defaultValue = "50") Integer limit) {
        return transactionSV.getPendingOfflineTopUpList(page, limit);
    }
}
