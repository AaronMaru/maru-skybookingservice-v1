package com.skybooking.skyhotelservice.v1_0_0.ui.controller.recommend;

import com.skybooking.skyhotelservice.v1_0_0.service.recommend.RecommendSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1.0.0/recommend-hotel")
public class RecommendController extends BaseController {

    @Autowired
    private RecommendSV recommendSV;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve location hotels
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<StructureRS> listing()
    {
        return response(recommendSV.listing());
    }


}
