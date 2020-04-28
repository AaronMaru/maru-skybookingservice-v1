package com.skybooking.stakeholderservice.v1_0_0.ui.controller.common.passenger;

import com.skybooking.core.validators.groups.OnCreate;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.passenger.PassengerSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.passenger.PassengerRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * Created by : maru
 * Date  : 4/24/2020
 * Time  : 12:43 PM
 */
@RestController
@RequestMapping("/v1.0.0/passenger")
public class PassengerController {

    @Autowired
    private PassengerSV passengerService;

    @Autowired
    private LocalizationBean localization;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * This endpoint created passenger only use for booking created PNR we dun need throw if exist
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param body - The request body to create a new passenger.
     * @return PassengerRS - The response body of created passenger
     */
    @PostMapping(
        path = "",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResRS create(@Validated({OnCreate.class}) @RequestBody PassengerRQ body) throws ParseException {

        this.passengerService.bookingCreatePassenger(body);
        return localization.resAPI(HttpStatus.CREATED, "res_succ", null);

    }
}
