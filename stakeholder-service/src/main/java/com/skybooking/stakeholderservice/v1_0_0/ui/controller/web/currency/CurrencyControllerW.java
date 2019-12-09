package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.currency;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.currency.CurrencySV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.currency.CurrencyRS;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/wv1.0.0/utils")
public class CurrencyControllerW {

    @Autowired
    private CurrencySV currencySV;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private Localization localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get currency list by locale's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/currency")
    public ResRS getCurrencyByLocaleId() {

        var currencyRSList = currencySV.findAllCurrencyByLocaleId(headerBean.getLocalizationId());
        var topCurrencyList = currencyRSList.stream().filter(currencyRS -> currencyRS.getIsTop() > 0).collect(Collectors.toList());

        Map<String, List<CurrencyRS>> responses = new TreeMap<>();
        responses.put("topCurrencies", topCurrencyList);
        responses.put("allCurrencies", currencyRSList);

        return localization.resAPI(HttpStatus.OK, "res_succ", responses);

    }
}
