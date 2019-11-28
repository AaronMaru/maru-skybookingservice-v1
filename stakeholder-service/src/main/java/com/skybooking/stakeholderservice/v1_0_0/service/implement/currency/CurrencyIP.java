package com.skybooking.stakeholderservice.v1_0_0.service.implement.currency;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.locale.LocaleEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.currency.CurrencyNQ;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.currency.CurrencyTO;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.locale.LocaleRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.currency.CurrencySV;
import com.skybooking.stakeholderservice.v1_0_0.transformer.currency.CurrencyTF;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.currency.CurrencyRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyIP implements CurrencySV {


    @Autowired
    private CurrencyNQ currencyNQ;

    @Autowired
    private LocaleRP localeRP;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get all currencies by locale's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param localeId
     * @return List
     */
    @Override
    public List<CurrencyRS> findAllCurrencyByLocaleId(long localeId) {

        LocaleEntity defaultLocale = localeRP.findLocaleByLocale("en");

        List<CurrencyTO> currencyTOList = currencyNQ.findAllByLocaleId(localeId);

        currencyTOList.forEach(currencyTO -> {
            if (currencyTO.getName() == null) {
                CurrencyTO defaultCurrency = currencyNQ.findAllByIdAndLocale(currencyTO.getCurrencyId(), defaultLocale.getId());
                currencyTO.setName(defaultCurrency.getName());
            }
        });

        return CurrencyTF.getResponseList(currencyTOList);
    }

}
