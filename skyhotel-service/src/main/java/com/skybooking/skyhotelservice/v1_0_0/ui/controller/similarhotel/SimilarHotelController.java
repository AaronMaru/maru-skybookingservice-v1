package com.skybooking.skyhotelservice.v1_0_0.ui.controller.similarhotel;

import com.skybooking.skyhotelservice.v1_0_0.service.similarhotel.SimilarHotelSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0.0/similar-hotel")
public class SimilarHotelController extends BaseController {

    @Autowired private SimilarHotelSV similarHotelSV;

    @GetMapping("{hotelCode}")
    public ResponseEntity<StructureRS> retrieve(@PathVariable Integer hotelCode)
    {
        return response(similarHotelSV.retrieve(hotelCode));
    }
}
