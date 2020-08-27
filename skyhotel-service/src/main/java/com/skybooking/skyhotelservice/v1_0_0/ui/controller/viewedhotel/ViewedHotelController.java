package com.skybooking.skyhotelservice.v1_0_0.ui.controller.viewedhotel;

import com.skybooking.skyhotelservice.v1_0_0.service.viewedhotel.ViewedHotelSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1.0.0/viewed-hotel")
public class ViewedHotelController extends BaseController {

    @Autowired
    private ViewedHotelSV viewedHotelSV;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve hotels user have viewed
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<StructureRS> listing()
    {
        return response(viewedHotelSV.listing());
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * delete viewed hotel of user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @DeleteMapping
    public ResponseEntity<StructureRS> deleteListing()
    {
        return response(viewedHotelSV.delete());
    }

}
