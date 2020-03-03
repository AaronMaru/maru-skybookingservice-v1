package com.skybooking.skyflightservice.v1_0_0.io.repository.currency;

import com.skybooking.skyflightservice.v1_0_0.io.entity.currency.ExchangeRatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRP extends JpaRepository<ExchangeRatesEntity, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM exchange_rates ORDER BY created_at DESC LIMIT 1")
    ExchangeRatesEntity getLastExchangeRates();
}
