package com.skybooking.stakeholderservice.v1_0_0.transformer.currency;

import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.currency.CurrencyTO;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.currency.CurrencyRS;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class CurrencyTF {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param currencyTO
     * @return CurrencyRS
     */
    public static CurrencyRS getResponse(CurrencyTO currencyTO) {
        CurrencyRS response = new CurrencyRS();
        BeanUtils.copyProperties(currencyTO, response);
        return response;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param currencyTOS
     * @return List
     */
    public static List<CurrencyRS> getResponseList(List<CurrencyTO> currencyTOS) {
        List<CurrencyRS> responses = new ArrayList<>();
        currencyTOS.forEach(currencyTO -> responses.add(getResponse(currencyTO)));
        return responses;
    }
}
