package com.skybooking.stakeholderservice.v1_0_0.service.implement.currency;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.locale.LocaleEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.currency.CurrencyNQ;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.currency.CurrencyTO;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.locale.LocaleRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.currency.CurrencySV;
import com.skybooking.stakeholderservice.v1_0_0.transformer.currency.CurrencyTF;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.currency.ChangeCurrencyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.currency.CurrencyRS;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyIP implements CurrencySV {


    @Autowired
    private CurrencyNQ currencyNQ;

    @Autowired
    private LocaleRP localeRP;

    @Autowired
    private UserBean userBean;

    @Autowired
    private UserRepository userRP;


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


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Change currency
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param changeCurrencyRQ
     * @return List
     */
    public void changeCurrency(ChangeCurrencyRQ changeCurrencyRQ) {

        UserEntity user = userBean.getUserPrincipal();

        LocaleEntity defaultLocale = localeRP.findLocaleByLocale("en");
        List<CurrencyTO> currencyTOList = currencyNQ.findAllByLocaleId(defaultLocale.getId());

        Boolean[] b = {false};

        currencyTOList.forEach(currencyTO -> {

            if ((long)currencyTO.getCurrencyId() == changeCurrencyRQ.getCurrencyId()) {
                user.getStakeHolderUser().setCurrencyId(changeCurrencyRQ.getCurrencyId());
                userRP.save(user);
                b[0] = true;
                return;
            }
        });

        if (b[0] == false) {
            throw new BadRequestException("sth_w_w", null);
        }

    }

}
