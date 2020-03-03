package com.skybooking.skyflightservice.v1_0_0.ui.controller;

import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.MetadataSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.ResponseSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.ShoppingSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightDetailRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.BaseRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping.SearchRS;
import com.skybooking.skyflightservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyflightservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0/shopping")
public class ShoppingController {

    @Autowired
    ShoppingSV shoppingSV;

    @Autowired
    ResponseSV responseSV;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    HeaderBean headerBean;

    @Autowired
    @Qualifier(value = "MetadataIP")
    private MetadataSV metadataSV;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Search flight available
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return
     */
    @PostMapping("/search")
    public ResponseEntity<SearchRS> search(@Valid @RequestBody FlightShoppingRQ request) {
        return new ResponseEntity<>(
                new SearchRS(
                        HttpStatus.OK,
                        shoppingSV.shoppingTransformMarkup(
                                request,
                                metadataSV.getUserAuthenticationMetadata(),
                                headerBean.getCurrencyCode(),
                                headerBean.getLocalizationId()
                        )
                ),
                HttpStatus.OK
        );
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get flight response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @return
     */
    @GetMapping("/search/{id}")
    public ResponseEntity<SearchRS> searchById(@PathVariable String id) {
        return new ResponseEntity<>(
                new SearchRS(HttpStatus.OK, responseSV.flightShoppingById(id)),
                HttpStatus.OK
        );
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get flight detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param flightDetailRQ
     * @return
     */
    @PostMapping(value = "/flight/detail", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseRS> getFlightDetail(@Valid @RequestBody FlightDetailRQ flightDetailRQ) {
        return new ResponseEntity<>(
                new BaseRS(HttpStatus.OK, shoppingSV.getFlightDetail(flightDetailRQ, metadataSV.getUserAuthenticationMetadata())),
                HttpStatus.OK
        );
    }
}
