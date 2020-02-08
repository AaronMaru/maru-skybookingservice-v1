package com.skybooking.skyflightservice.v1_0_0.ui.controller;

import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.ResponseSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.shopping.ShoppingSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping.FlightShoppingRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping.FilterListRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping.FlightDetailRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping.PolicyRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping.SearchRS;
import com.skybooking.skyflightservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyflightservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/search")
    public ResponseEntity<SearchRS> search(@Valid @RequestBody FlightShoppingRQ request) {

        var userType = jwtUtils.getClaim("userType", String.class) == null ? "anonymous" : jwtUtils.getClaim("userType", String.class);
        var userId = userType == "anonymouse" ? null : userType == "skyowner" ? jwtUtils.getClaim("stakeholderId", Integer.class) : jwtUtils.getClaim("companyId", Integer.class);

        return new ResponseEntity<>(new SearchRS(HttpStatus.OK, shoppingSV.shoppingTransformMarkup(request, userType, userId, headerBean.getCurrencyCode(), headerBean.getLocalizationId())), HttpStatus.OK);

    }

    @GetMapping("/search/{id}")
    public ResponseEntity<SearchRS> searchById(@PathVariable String id) {
        return new ResponseEntity<>(new SearchRS(HttpStatus.OK, responseSV.flightShoppingById(id)), HttpStatus.OK);
    }

    @GetMapping(value = "/filter-list/{requestID}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FilterListRS> getFilterList(@PathVariable String requestID) {
        return new ResponseEntity<>(new FilterListRS(HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping(value = "/flight/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FlightDetailRS> getFlightDetail(@PathVariable String id) {
        return new ResponseEntity<>(new FlightDetailRS(HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping(value = "/policy/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PolicyRS> getPolicy(@PathVariable String id) {
        return new ResponseEntity<>(new PolicyRS(HttpStatus.OK), HttpStatus.OK);
    }
}
