package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.account;

import io.github.gasparbarancelli.NativeQuery;
import io.github.gasparbarancelli.NativeQueryFolder;
import io.github.gasparbarancelli.NativeQueryParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@NativeQueryFolder("account")
@Component
public interface AccountNQ extends NativeQuery {

    @Transactional
    Optional<BalanceTO> getBalance(@NativeQueryParam(value = "userCode") String userCode,
                                   @NativeQueryParam(value = "userType") String userType);

    @Transactional
    AccountSummaryTO getAccountSummary();

    @Transactional
    List<TopBalanceTO> getTopBalance();

    @Transactional
    List<TopEarningTO> getTopEarning();

}
