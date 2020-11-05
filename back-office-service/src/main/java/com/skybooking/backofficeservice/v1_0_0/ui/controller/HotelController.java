package com.skybooking.backofficeservice.v1_0_0.ui.controller;

import com.skybooking.backofficeservice.v1_0_0.service.hotel.HotelSV;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.hotel.HotelDetailRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0.0/hotel")
public class HotelController {

    @Autowired
    private HotelSV hotelSV;

    @GetMapping("/detail/{bookingCode}")
    public HotelDetailRS hotelDetail(@PathVariable String bookingCode)
    {
        try {
            HotelDetailRS hotelDetailRS = hotelSV.hotelDetail(bookingCode);

            return hotelDetailRS;

        }catch (Exception exception){
            throw exception;
        }

    }

    @GetMapping()
    public String test(){
        return "test";
    }
}
