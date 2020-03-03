package com.skybooking.skyflightservice.v1_0_0.service.implement.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skybooking.skyflightservice.v1_0_0.io.repository.currency.ExchangeRateRP;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.currency.CurrencySV;
import com.skybooking.skyflightservice.v1_0_0.service.model.currency.ExchangeCurrencyTA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyIP implements CurrencySV {

    @Autowired
    private ExchangeRateRP exchangeRateRP;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param exchangeCurrency
     * @return
     */
    @Override
    public double getExchangeRateConverter(ExchangeCurrencyTA exchangeCurrency) {

        if (exchangeCurrency.getFrom().equalsIgnoreCase(exchangeCurrency.getTo())) return exchangeCurrency.getAmount();

        double price = (exchangeCurrency.getAmount() * exchangeCurrency.getToRate()) / exchangeCurrency.getFromRate();

        return price;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get exchange rate by currency code
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param code
     * @return double
     */
    @Override
    public double getExchangeRateByCode(String code) {

        var exchangeList = exchangeRateRP.getLastExchangeRates();

        try {

            var mapper = new ObjectMapper();
            var exchange = mapper.readTree(exchangeList.getRates());

            if (exchange.has(code.toUpperCase())) {
                return exchange.get(code.toUpperCase()).doubleValue();
            } else {
                return 0;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}
