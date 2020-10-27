package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction;

import io.github.gasparbarancelli.NativeQuery;
import io.github.gasparbarancelli.NativeQueryFolder;
import io.github.gasparbarancelli.NativeQueryParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@NativeQueryFolder("transaction")
@Component
public interface TransactionNQ extends NativeQuery {
    @Transactional
    Optional<AmountOnlineTopUpTO> getAmountOnlineTopUpForCurrentDateByAccount(
            @NativeQueryParam(value = "accountId") Integer accountId);

    @Transactional
    List<TopUpTransactionDetailTO> getRecentTopUp();

    @Transactional
    List<TopUpTransactionDetailTO> searchOfflineTopUpTransaction(@NativeQueryParam(value = "valueSearch")
                                                                                String valueSearch);

    @Transactional
    Optional<OnlineTopUpTransactionDetailTO> getOnlineTopUpTransactionDetail(@NativeQueryParam(value = "transactionCode") String transactionCode);

    @Transactional
    List<AccountTransactionTO> getRecentTransactionByAccount(@NativeQueryParam(value = "userCode") String userCode,
                                                             @NativeQueryParam(value = "languageCode") String languageCode,
                                                             @NativeQueryParam(value = "limit") Integer limit,
                                                             @NativeQueryParam(value = "offset") Integer offset);

    @Transactional
    List<AccountTransactionTO> getAllRecentTransactionByAccount(@NativeQueryParam(value = "userCode") String userCode,
                                                                @NativeQueryParam(value = "languageCode") String languageCode);

    @Transactional
    List<PendingOfflineTopUpTransactionTO> getPendingOfflineTopUpList(@NativeQueryParam(value = "limit") Integer limit,
                                                                      @NativeQueryParam(value = "offset") Integer offset);

    @Transactional
    List<PendingOfflineTopUpTransactionTO> countAllPendingOfflineTopUpTransaction();
}
