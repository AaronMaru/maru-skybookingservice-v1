package com.skybooking.skypointservice.v1_0_0.ui.controller.history;

import com.skybooking.skypointservice.v1_0_0.service.hisotry.HistorySV;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.history.FilterTransactionHistoryRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1.0.0/history")
public class HistoryController {
    @Autowired
    private HistorySV historySV;


    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public StructureRS getTransactionHistoryByAccount() {
        return historySV.getTransactionHistoryByUserAccount();
    }

    @RequestMapping(value = "/transaction/filter", method = RequestMethod.POST)
     public StructureRS filterTransactionHistoryByAccount(@RequestBody FilterTransactionHistoryRQ filterTransactionHistoryRQ) {
        return historySV.filterTransactionHistoryByAccount(filterTransactionHistoryRQ);
    }

    @RequestMapping(value = "/transaction/detail/{transactionValueId}", method = RequestMethod.POST)
    public StructureRS getTransactionHistoryDetail(@PathVariable(name = "transactionValueId") Integer transactionValueId) {
        return historySV.getTransactionHistoryDetail(transactionValueId);
    }

}
