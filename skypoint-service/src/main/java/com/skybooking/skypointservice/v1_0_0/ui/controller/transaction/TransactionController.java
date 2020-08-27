package com.skybooking.skypointservice.v1_0_0.ui.controller.transaction;

import com.skybooking.skypointservice.v1_0_0.service.transaction.TransactionSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.transaction.TransactionRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/recent/offline/topup", method = RequestMethod.GET)
    public StructureRS getRecentOfflineTopUp() {
        return transactionSV.getRecentOfflineTopUp();
    }

    @RequestMapping(value = "/offline/detail", method = RequestMethod.POST)
    public StructureRS getOfflineTopUpTransactionDetail(HttpServletRequest httpServletRequest,
                                                        @RequestBody TransactionRQ transactionRQ) {
        return transactionSV.getOfflineTopUpTransactionDetail(httpServletRequest, transactionRQ);
    }

    @RequestMapping(value = "/offline/search", method = RequestMethod.GET)
    public StructureRS searchOfflineTopUpTransaction(@RequestParam String searchValue) {
        return transactionSV.searchOfflineTransaction(searchValue);
    }

    @RequestMapping(value = "/online/detail", method = RequestMethod.POST)
    public StructureRS getTopUpOnlineTransactionDetail(@RequestBody TransactionRQ transactionRQ) {
        return transactionSV.getOnlineTopUpTransactionDetail(transactionRQ);
    }
}
