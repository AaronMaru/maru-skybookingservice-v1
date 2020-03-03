package com.skybooking.skyflightservice.v1_0_0.service.interfaces.currency;

import com.skybooking.skyflightservice.v1_0_0.service.model.currency.ExchangeCurrencyTA;
import org.springframework.stereotype.Service;

@Service
public interface CurrencySV {

    double getExchangeRateConverter(ExchangeCurrencyTA exchangeCurrency);

    double getExchangeRateByCode(String code);

}
