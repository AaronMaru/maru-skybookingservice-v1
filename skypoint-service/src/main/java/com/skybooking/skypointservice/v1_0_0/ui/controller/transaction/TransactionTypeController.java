package com.skybooking.skypointservice.v1_0_0.ui.controller.transaction;

import com.skybooking.skypointservice.v1_0_0.service.transaction.TransactionTypeSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0.0/transaction-type")
public class TransactionTypeController {
    @Autowired
    private TransactionTypeSV transactionTypeSV;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public StructureRS getTransactionType() {
        return transactionTypeSV.getTransactionTypeCode();
    }
}
