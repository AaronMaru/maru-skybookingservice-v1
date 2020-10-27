package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.report;

import io.github.gasparbarancelli.NativeQuery;
import io.github.gasparbarancelli.NativeQueryFolder;
import io.github.gasparbarancelli.NativeQueryParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NativeQueryFolder("report")
@Component
public interface ReportNQ extends NativeQuery {
    @Transactional
    AccountSummaryTO getAccountSummary();

    @Transactional
    List<TopBalanceTO> getTopBalance();

    @Transactional
    List<TopEarningTO> getTopEarning();

    @Transactional
    List<TransactionSummaryReportTO> transactionListSummaryReport(@NativeQueryParam("startDate") String startDate,
                                                                  @NativeQueryParam("endDate") String endDate);

    @Transactional
    TransactionSummaryReportTO transactionSummaryReport(@NativeQueryParam("startDate") String startDate,
                                                        @NativeQueryParam("endDate") String endDate);

    @Transactional
    List<TransactionSummaryReportTO> transactionSummaryListReportByAccount(@NativeQueryParam("userCode") String userCode);

    @Transactional
    TopUpPointSummaryReportTO topUpPointSummaryReport(@NativeQueryParam("startDate") String startDate,
                                                      @NativeQueryParam("endDate") String endDate,
                                                      @NativeQueryParam("userCode") String userCode);

    @Transactional
    List<TopUpPointDetailReportTO> topUpPointDetailReport(@NativeQueryParam("startDate") String startDate,
                                                          @NativeQueryParam("endDate") String endDate,
                                                          @NativeQueryParam("languageCode") String languageCode,
                                                          @NativeQueryParam("userCode") String userCode);

    @Transactional
    SpentPointSummaryReportTO spentPointSummaryReport(@NativeQueryParam("startDate") String startDate,
                                                      @NativeQueryParam("endDate") String endDate,
                                                      @NativeQueryParam("userCode") String userCode);

    @Transactional
    List<SpentPointDetailReportTO> spentPointDetailReport(@NativeQueryParam("startDate") String startDate,
                                                          @NativeQueryParam("endDate") String endDate,
                                                          @NativeQueryParam("languageCode") String languageCode,
                                                          @NativeQueryParam("userCode") String userCode);

    @Transactional
    EarnedPointSummaryReportTO earnedPointSummaryReport(@NativeQueryParam("startDate") String startDate,
                                                        @NativeQueryParam("endDate") String endDate,
                                                        @NativeQueryParam("userCode") String userCode);

    @Transactional
    List<EarnedPointDetailReportTO> earnedPointDetailReport(@NativeQueryParam("startDate") String startDate,
                                                            @NativeQueryParam("endDate") String endDate,
                                                            @NativeQueryParam("languageCode") String languageCode,
                                                            @NativeQueryParam("userCode") String userCode);

    @Transactional
    RefundedPointSummaryReportTO refundedPointSummaryReport(@NativeQueryParam("startDate") String startDate,
                                                            @NativeQueryParam("endDate") String endDate,
                                                            @NativeQueryParam("userCode") String userCode);

    @Transactional
    List<RefundedPointDetailReportTO> refundedPointDetailReport(@NativeQueryParam("startDate") String startDate,
                                                                @NativeQueryParam("endDate") String endDate,
                                                                @NativeQueryParam("languageCode") String languageCode,
                                                                @NativeQueryParam("userCode") String userCode);

    @Transactional
    UpgradedLevelSummaryReportTO upgradedLevelSummaryReport(@NativeQueryParam("startDate") String startDate,
                                                            @NativeQueryParam("endDate") String endDate);


    @Transactional
    List<TransactionReportTO> getTransactionReport(@NativeQueryParam("startDate") String startDate,
                                                   @NativeQueryParam("endDate") String endDate,
                                                   @NativeQueryParam("languageCode") String languageCode);
}
