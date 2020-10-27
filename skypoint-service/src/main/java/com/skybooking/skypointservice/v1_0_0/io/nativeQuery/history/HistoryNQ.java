package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.history;

import io.github.gasparbarancelli.NativeQuery;
import io.github.gasparbarancelli.NativeQueryFolder;
import io.github.gasparbarancelli.NativeQueryParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@NativeQueryFolder("history")
@Component
public interface HistoryNQ extends NativeQuery {

    @Transactional
    List<TransactionHistoryTO> getTransactionHistoryByAccount(@NativeQueryParam("userCode") String userCode,
                                                              @NativeQueryParam("transactionTypeCode") String transactionTypeCode,
                                                              @NativeQueryParam("languageCode") String languageCode,
                                                              @NativeQueryParam("limit") Integer limit,
                                                              @NativeQueryParam("offset") Integer offset);

    @Transactional
    List<TransactionHistoryTO> filterTransactionHistoryByAccount(@NativeQueryParam("userCode") String userCode,
                                                                 @NativeQueryParam("transactionTypeCode") String transactionTypeCode,
                                                                 @NativeQueryParam("startDate") String startDate,
                                                                 @NativeQueryParam("endDate") String endDate,
                                                                 @NativeQueryParam("languageCode") String languageCode,
                                                                 @NativeQueryParam("limit") Integer limit,
                                                                 @NativeQueryParam("offset") Integer offset);

    @Transactional
    Optional<TransactionHistoryDetailTO> getTransactionHistoryDetail(@NativeQueryParam("transactionCode") String transactionCode,
                                                                     @NativeQueryParam("languageCode") String languageCode);

    @Transactional
    List<SkyOwnerTransactionHistoryTO> skyOwnerTransactionHistory(@NativeQueryParam(value = "userCode") String userCode,
                                                                  @NativeQueryParam(value = "stakeholderUserId") Integer stakeholderUserId,
                                                                  @NativeQueryParam(value = "languageCode") String languageCode,
                                                                  @NativeQueryParam(value = "limit") Integer limit,
                                                                  @NativeQueryParam(value = "offset") Integer offset);

    @Transactional
    List<SkyOwnerTransactionHistoryTO> skyOwnerTransactionHistoryAll(@NativeQueryParam(value = "userCode") String userCode,
                                                                     @NativeQueryParam(value = "stakeholderUserId") Integer stakeholderUserId,
                                                                     @NativeQueryParam(value = "languageCode") String languageCode);

    @Transactional
    List<CountTransactionHistory> countAllTransactionHistoryByAccount(@NativeQueryParam(value = "userCode") String userCode,
                                                                      @NativeQueryParam("transactionTypeCode") String transactionTypeCode);

    @Transactional
    List<CountTransactionHistory> countAllFilterTransactionHistoryByAccount(@NativeQueryParam(value = "userCode") String userCode,
                                                                            @NativeQueryParam("transactionTypeCode") String transactionTypeCode,
                                                                            @NativeQueryParam("startDate") String startDate,
                                                                            @NativeQueryParam("endDate") String endDate);

    @Transactional
    List<TransactionHistoryTO> getAllTransactionHistoryByAccount(@NativeQueryParam("userCode") String userCode,
                                                                 @NativeQueryParam("languageCode") String languageCode,
                                                                 @NativeQueryParam("startDate") String startDate,
                                                                 @NativeQueryParam("endDate") String endDate);
}
