package com.skybooking.skyflightservice.v1_0_0.ui.controller;

import com.skybooking.skyflightservice.v1_0_0.client.distributed.action.LocationAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1.0.0")
public class LocationController {

    @Autowired
    LocationAction action;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Quick Search
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param groupByKey
     * @return
     */
    @GetMapping("/sb-quick-search-location")
    public Object getQuickSearchLocation(@RequestParam(required = false) String groupByKey) {

        return action.getQuickSearchAction(groupByKey);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Auto-Complete Search
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param keyword
     * @param groupBy
     * @return
     */
    @GetMapping("/sb-auto-complete")
    public Object getAutoComplete(@RequestParam String keyword, @RequestParam(required = false) boolean groupBy) {

        return action.getAutoCompleteAction(keyword, groupBy);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Country
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @GetMapping("/sb-countries")
    public Object getCountries() {

        return action.getCountryAction();

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get City
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param countryId
     * @return
     */
    @GetMapping("/sb-cities/{countryId}")
    public Object getCities(@PathVariable int countryId) {

        return action.getCityAction(countryId);

    }

}
