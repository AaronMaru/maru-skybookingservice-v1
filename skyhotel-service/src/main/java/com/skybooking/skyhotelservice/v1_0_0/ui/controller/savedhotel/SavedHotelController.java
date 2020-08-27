package com.skybooking.skyhotelservice.v1_0_0.ui.controller.savedhotel;

import com.skybooking.skyhotelservice.v1_0_0.service.savedhotel.SavedHotelSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.HotelCodeRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0/saved-hotel")
public class SavedHotelController extends BaseController {

    @Autowired
    private SavedHotelSV savedHotelSV;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve hotels user saved
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<StructureRS> listing()
    {
        return response(savedHotelSV.listing());
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * user save or remove hotel
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotelCodeRQ
     * @return
     */
    @PostMapping
    public ResponseEntity<StructureRS> addOrRemove(@Valid @RequestBody HotelCodeRQ hotelCodeRQ)
    {
        return response(savedHotelSV.addOrUpdate(hotelCodeRQ));
    }

}
