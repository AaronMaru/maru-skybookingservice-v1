package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.currency;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.currency.CurrencySV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.currency.ChangeCurrencyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.currency.CurrencyRS;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private LocalizationBean localization;



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get currency list by locale's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping(value = "/currency")
    public ResRS getCurrencyByLocaleId() {

        var currencyRSList = currencySV.findAllCurrencyByLocaleId(headerBean.getLocalizationId(null));
        var topCurrencyList = currencyRSList.stream().filter(currencyRS -> currencyRS.getIsTop() > 0).collect(Collectors.toList());

        Map<String, List<CurrencyRS>> responses = new TreeMap<>();
        responses.put("topCurrencies", topCurrencyList);
        responses.put("allCurrencies", currencyRSList);

        return localization.resAPI(HttpStatus.OK, "res_succ", responses);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get currency list by locale's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @PostMapping(value = "currency")
    public ResRS changeCurrency(@Valid @RequestBody ChangeCurrencyRQ changeCurrencyRQ) {
        currencySV.changeCurrency(changeCurrencyRQ);
        return localization.resAPI(HttpStatus.OK, "res_succ", null);
    }


}
