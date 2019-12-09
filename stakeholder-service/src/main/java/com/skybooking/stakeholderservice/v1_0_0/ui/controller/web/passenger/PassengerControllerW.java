package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.passenger;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.passenger.PassengerSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.interfaces.OnCreate;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.interfaces.OnUpdate;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.passenger.PassengerRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.passenger.PassengerRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wv1.0.0/passenger")
public class PassengerControllerW {


    @Autowired
    private PassengerSV passengerService;

    @Autowired
    private Localization localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * this method will create a new passenger of stake holder.
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

    public ResRS create(@Validated({OnCreate.class}) @RequestBody PassengerRQ body) {

        PassengerRS response = this.passengerService.createItem(body);

        return localization.resAPI(HttpStatus.CREATED,"res_succ", response);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * this method will find all passengers of logged-in stakeholder.
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return List of passengers
     */
    @GetMapping(path = "")
    public ResRS findAll() {

        List<PassengerRS> responses = this.passengerService.getItems(null);

        return localization.resAPI(HttpStatus.OK,"res_succ", responses);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * this method will get passenger's detail information by id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id The passenger's id
     * @return PassengerDao
     */
    @GetMapping(path = "/{id}")
    public ResRS findById(@PathVariable Long id) {

        PassengerRS response = this.passengerService.getItem(id);

        return localization.resAPI(HttpStatus.OK,"res_succ", response);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * this method is going to update passenger's information.
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id   The passenger's id that going to update information
     * @param body The passenger's information that going to update.
     * @return String
     */
    @PatchMapping(
            path = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResRS updateById(@PathVariable Long id, @Validated({OnUpdate.class}) @RequestBody PassengerRQ body) {

        PassengerRS response = this.passengerService.updateItem(id, body);

        return localization.resAPI(HttpStatus.OK,"update_succ", response);
    }


    /**
     * ------------------------------------------------------------------------------------------------------------------
     * this method is going to delete passenger.
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id The passenger's id that going to delete.
     * @return string
     */
    @DeleteMapping(
            path = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResRS deleteById(@PathVariable Long id) {

        this.passengerService.deleteItem(id);

        return localization.resAPI(HttpStatus.OK,"del_succ", "");
    }

}
