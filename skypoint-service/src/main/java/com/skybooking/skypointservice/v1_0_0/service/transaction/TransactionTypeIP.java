package com.skybooking.skypointservice.v1_0_0.service.transaction;

import com.skybooking.skypointservice.constant.ResponseConstant;
import com.skybooking.skypointservice.util.HeaderDataUtil;
import com.skybooking.skypointservice.v1_0_0.io.entity.transaction.TransactionTypeEntity;
import com.skybooking.skypointservice.v1_0_0.io.repository.transaction.TransactionTypeRP;
import com.skybooking.skypointservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction.TransactionTypeRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TransactionTypeIP extends BaseServiceIP implements TransactionTypeSV {
    @Autowired
    private TransactionTypeRP transactionTypeRP;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HeaderDataUtil headerDataUtil;

    @Override
    public StructureRS getTransactionTypeCode() {
        String languageCode = headerDataUtil.languageCode(httpServletRequest);
        List<TransactionTypeEntity> transactionTypeList = transactionTypeRP.findAllByLanguageCode(languageCode);

        TransactionTypeRS transactionTypeRS = new TransactionTypeRS();
        transactionTypeRS.setTransactionTypeList(transactionTypeList);
        return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, transactionTypeRS);
    }
}
