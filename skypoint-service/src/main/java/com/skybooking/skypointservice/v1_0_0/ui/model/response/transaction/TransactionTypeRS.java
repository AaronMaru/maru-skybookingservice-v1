package com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction;

import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionTypeEntity;
import lombok.Data;

import java.util.List;

@Data
public class TransactionTypeRS {
    List<TransactionTypeEntity> transactionTypeList;
}
