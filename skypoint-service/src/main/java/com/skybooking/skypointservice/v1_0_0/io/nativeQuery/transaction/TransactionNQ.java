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
    List<OfflineTopUpTransactionDetailTO> getRecentOfflineTopUp();

    @Transactional
    List<OfflineTopUpTransactionDetailTO> searchOfflineTopUpTransaction(@NativeQueryParam(value = "valueSearch")
                                                                                String valueSearch);

    @Transactional
    OnlineTopUpTransactionDetailTO getOnlineTopUpTransactionDetail(@NativeQueryParam(value = "transactionCode") String transactionCode);

    @Transactional
    List<AccountTransactionTO> getAccountRecentTransactionByAccount(@NativeQueryParam(value = "userCode") String userCode);
}
