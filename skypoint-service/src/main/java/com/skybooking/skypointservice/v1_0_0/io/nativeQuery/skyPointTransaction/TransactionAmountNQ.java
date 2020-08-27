package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.skyPointTransaction;

import io.github.gasparbarancelli.NativeQuery;
import io.github.gasparbarancelli.NativeQueryFolder;
import io.github.gasparbarancelli.NativeQueryParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@NativeQueryFolder("skyPointTransaction")
@Component
public interface TransactionAmountNQ extends NativeQuery {
    @Transactional
    Optional<TransactionAmountTO> getAmountRedeemForCurrentDateByAccount(
            @NativeQueryParam(value = "stakeholderUserId") Integer stakeholderUserId,
            @NativeQueryParam(value = "stakeholderCompanyId") Integer stakeholderCompanyId);
}
