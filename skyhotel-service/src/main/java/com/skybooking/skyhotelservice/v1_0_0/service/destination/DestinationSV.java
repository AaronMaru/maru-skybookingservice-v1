package com.skybooking.skyhotelservice.v1_0_0.service.destination;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface DestinationSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve quick search destination
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    StructureRS quickSearch(String groupByKey);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * search destination autocomplete
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param keyword
     * @return
     */
    StructureRS autocompleteSearch(String keyword);

}
