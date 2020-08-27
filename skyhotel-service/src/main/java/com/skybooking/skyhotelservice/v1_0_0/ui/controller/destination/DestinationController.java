package com.skybooking.skyhotelservice.v1_0_0.ui.controller.destination;

import com.skybooking.skyhotelservice.v1_0_0.service.destination.DestinationSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0.0/destination")
public class DestinationController extends BaseController {

    @Autowired
    private DestinationSV destinationSV;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve quick search destination
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @GetMapping(value = "quick-search")
    public ResponseEntity<StructureRS> quickSearch(
        @RequestParam(name = "groupByKey", defaultValue = "", required = false) String groupByKey)
    {
        return response(destinationSV.quickSearch(groupByKey));
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * search destination autocomplete
     * -----------------------------------------------------------------------------------------------------------------
     * @param keyword
     * @return
     */
    @GetMapping(value = "autocomplete-search")
    public ResponseEntity<StructureRS> autocompleteSearch(@RequestParam(required = false) String keyword)
    {
        return response(destinationSV.autocompleteSearch(keyword));
    }
}
