package com.skybooking.stakeholderservice.v1_0_0.ui.controller.app.currency;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.currency.CurrencySV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.currency.ChangeCurrencyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/mv1.0.0/utils")
public class CurrencyControllerM {

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
     * @return ResponseEntity
     */
    @GetMapping(value = "currency")
    public ResRS getCurrencyByLocaleId() {
        var responses = currencySV.findAllCurrencyByLocaleId(headerBean.getLocalizationId(null));
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
