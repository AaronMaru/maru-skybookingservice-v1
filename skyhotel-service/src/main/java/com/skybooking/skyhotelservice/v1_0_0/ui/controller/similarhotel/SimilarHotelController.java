package com.skybooking.skyhotelservice.v1_0_0.ui.controller.similarhotel;

import com.skybooking.skyhotelservice.v1_0_0.service.similarhotel.SimilarHotelSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.similarhotel.SimilarHotelRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0/similar-hotel")
public class SimilarHotelController extends BaseController {

    @Autowired private SimilarHotelSV similarHotelSV;

    @PostMapping
    public ResponseEntity<StructureRS> getSimilarHotel(@Valid @RequestBody SimilarHotelRQ similarHotelRQ)
    {
        return response(similarHotelSV.getSimilarHotel(similarHotelRQ));
    }
}
