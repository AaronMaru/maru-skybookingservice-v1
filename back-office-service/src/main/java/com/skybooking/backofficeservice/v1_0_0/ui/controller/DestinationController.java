package com.skybooking.backofficeservice.v1_0_0.ui.controller;

import com.skybooking.backofficeservice.v1_0_0.service.destination.DestinationSV;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.destination.DestinationRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1.0.0/destination")
public class DestinationController {

    @Autowired private DestinationSV destinationSV;

    public ResponseEntity<List<DestinationRS>> listing()
    {
        List<DestinationRS> destinations = destinationSV.listing();

        return ResponseEntity.status(HttpStatus.OK).body(destinations);
    }
}
