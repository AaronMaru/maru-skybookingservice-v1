package com.skybooking.backofficeservice.v1_0_0.ui.controller;

import com.skybooking.backofficeservice.v1_0_0.service.popularCity.PopularCitySV;
import com.skybooking.backofficeservice.v1_0_0.ui.model.request.quick.QuickRQ;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.PopularCityRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1.0.0/popular-city")
public class PopularCityController {

    @Autowired private PopularCitySV popularCitySV;

    @GetMapping()
    public ResponseEntity<List<PopularCityRS>> getPopularCity()
    {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(popularCitySV.listing());
    }

    @PostMapping()
    public ResponseEntity created(@Valid @RequestBody QuickRQ quickRQ)
    {
        popularCitySV.created(quickRQ);

        return new ResponseEntity<>("Success Created!", HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public  ResponseEntity deleted(@PathVariable Integer id)
    {
       var popular = popularCitySV.deleted(id);

       if(popular == false){
           return new ResponseEntity<>("Record not Match", HttpStatus.BAD_REQUEST);
       }

       return new ResponseEntity<>( "Success Deleted", HttpStatus.OK);
    }
}
