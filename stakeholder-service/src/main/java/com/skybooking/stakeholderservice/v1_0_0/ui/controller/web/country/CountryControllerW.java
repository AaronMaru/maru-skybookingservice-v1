package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.country;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.country.CountrySV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.country.CountryRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/wv1.0.0/utils")
public class CountryControllerW {

    @Autowired
    private CountrySV countrySV;

    @Autowired
    private Localization localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * list country'name include english and locale name.
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return list of country
     */
    @GetMapping(value = "/country")
    public ResRS getCountry() {
        List<CountryRS> countries = countrySV.getItems();
        return localization.resAPI(HttpStatus.OK, "res_succ", countries);
    }

}
