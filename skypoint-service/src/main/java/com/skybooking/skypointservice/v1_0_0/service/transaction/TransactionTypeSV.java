package com.skybooking.skypointservice.v1_0_0.service.transaction;

import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface TransactionTypeSV {
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Fronted: Get transactionType list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return StructureRS
     */
    StructureRS getTransactionTypeCode();
}
