package com.skybooking.stakeholderservice.v1_0_0.ui.controller.app.passenger;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.passenger.PassengerSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.interfaces.OnCreate;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.interfaces.OnUpdate;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.passenger.PassengerRQ;
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
@RequestMapping("/mv1.0.0/passenger")
public class PassengerControllerM {


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

    public ResponseEntity<PassengerRS> create(@Validated({OnCreate.class}) @RequestBody PassengerRQ body) {

        PassengerRS response = this.passengerService.createItem(body);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * this method will find all passengers of logged-in stakeholder.
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return List of passengers
     */
    @GetMapping(
            path = "",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<PassengerRS>> findAll() {

        List<PassengerRS> responses = this.passengerService.getItems(null);

        return new ResponseEntity<>(responses, HttpStatus.OK);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * this method will get passenger's detail information by id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id The passenger's id
     * @return PassengerDao
     */
    @GetMapping(
            path = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<PassengerRS> findById(@PathVariable Long id) {

        PassengerRS response = this.passengerService.getItem(id);

        return new ResponseEntity<>(response, HttpStatus.OK);

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
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<String> updateById(@PathVariable Long id, @Validated({OnUpdate.class}) @RequestBody PassengerRQ body) {

        PassengerRS response = this.passengerService.updateItem(id, body);

        return new ResponseEntity(localization.resAPI("update_succ", response), HttpStatus.OK);
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
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        this.passengerService.deleteItem(id);

        return new ResponseEntity(localization.resAPI("del_succ", ""), HttpStatus.OK);
    }

}
