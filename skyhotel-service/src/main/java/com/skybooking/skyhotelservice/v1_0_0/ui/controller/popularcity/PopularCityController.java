package com.skybooking.skyhotelservice.v1_0_0.ui.controller.popularcity;

import com.skybooking.skyhotelservice.v1_0_0.service.popularcity.PopularcitySV;
import com.skybooking.skyhotelservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0.0/popular-city")
public class PopularCityController extends BaseController {

    @Autowired
    private PopularcitySV popularcitySV;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve the popular cities
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<StructureRS> listing()
    {
        return response(popularcitySV.listing());
    }
}
