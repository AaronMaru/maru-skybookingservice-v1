package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.currency;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.currency.ChangeCurrencyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.currency.CurrencyRS;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface CurrencySV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get all currencies by locale's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param localeId
     * @return List
     */
    @Transactional
    List<CurrencyRS> findAllCurrencyByLocaleId(long localeId);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get all currencies by locale's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param changeCurrencyRQ
     * @return List
     */
    void changeCurrency(ChangeCurrencyRQ changeCurrencyRQ);
}
