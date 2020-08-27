package com.skybooking.skyhotelservice.v1_0_0.ui.controller.recentsearch;

import com.skybooking.skyhotelservice.v1_0_0.service.recentsearch.RecentSearchSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0.0/recent-search")
public class RecentSearchController extends BaseController {

    @Autowired
    private RecentSearchSV recentSearchSV;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve recent search of user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<StructureRS> listing()
    {
        return response(recentSearchSV.listing());
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * delete recent search
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @DeleteMapping()
    public ResponseEntity<StructureRS> delete()
    {
        return response(recentSearchSV.delete());
    }

}
