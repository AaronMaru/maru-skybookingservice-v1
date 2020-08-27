package com.skybooking.skyhotelservice.v1_0_0.ui.controller.availability;

import com.skybooking.skyhotelservice.v1_0_0.service.availability.AvailabilitySV;
import com.skybooking.skyhotelservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.HotelDetailRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("v1.0.0/availability")
public class AvailabilityController extends BaseController {

    @Autowired
    private AvailabilitySV availabilitySV;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * search hotel availabilities
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param availabilityRQ
     * @param paramsRQ
     * @return
     */
    @PostMapping
    public ResponseEntity<StructureRS> availability(
        @Validated @RequestBody AvailabilityRQ availabilityRQ,
        @RequestParam(required = false) Map<String, Object> paramsRQ)
    {
        return response(availabilitySV.availability(availabilityRQ, paramsRQ));
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve hotel detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param hotelDetailRQ
     * @return
     */
    @PostMapping("detail")
    public ResponseEntity<StructureRS> detail(@RequestBody HotelDetailRQ hotelDetailRQ)
    {
        return response(availabilitySV.detail(hotelDetailRQ));
    }
}
