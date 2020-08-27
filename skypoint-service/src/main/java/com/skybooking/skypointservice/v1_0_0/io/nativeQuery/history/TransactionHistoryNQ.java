package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.history;

import io.github.gasparbarancelli.NativeQuery;
import io.github.gasparbarancelli.NativeQueryFolder;
import io.github.gasparbarancelli.NativeQueryParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@NativeQueryFolder("history")
@Component
public interface TransactionHistoryNQ extends NativeQuery {

    @Transactional
    List<TransactionHistoryTO> getTransactionHistoryByAccount(@NativeQueryParam("userCode") String userCode);

    @Transactional
    List<TransactionHistoryTO> filterTransactionHistoryByAccount(@NativeQueryParam("userCode") String userCode,
                                                                 @NativeQueryParam("startDate") Date startDate,
                                                                 @NativeQueryParam("endDate") Date endDate);

    @Transactional
    TransactionHistoryDetailTO getTransactionHistoryDetail(@NativeQueryParam("transactionValueId") Integer transactionValueId);
}
