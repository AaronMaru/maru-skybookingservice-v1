package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.passenger;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.CrudSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.passenger.PassengerRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.passenger.PassengerPagingRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.passenger.PassengerRS;


public interface PassengerSV extends CrudSV<PassengerRS, PassengerPagingRS, PassengerRQ, Long> {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * This service for created passenger if exist throw nothing
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param passengerRQ - The request body to create a new passenger.
     * @return PassengerRS - The response body of created passenger
     */
    void bookingCreatePassenger(PassengerRQ passengerRQ);

}
