package com.skybooking.stakeholderservice.v1_0_0.transformer.passenger;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.passenger.PassengerEntity;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.passenger.PassengerRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.passenger.PassengerRS;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class PassengerTF {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get passenger entity
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param request
     * @return passengerEntity
     */

    public static PassengerEntity getEntity(PassengerRQ request) {

        PassengerEntity passenger = new PassengerEntity();
        BeanUtils.copyProperties(request, passenger);

        return passenger;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get passenger response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param entity
     * @return PassengerRS
     */
    public static PassengerRS getResponse(PassengerEntity entity) {

        PassengerRS response = new PassengerRS();
        BeanUtils.copyProperties(entity, response);

        return response;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get passenger response list
     *
     * @param entities
     * @return List<PassengerRS>
     */

    public static List<PassengerRS> getResponseList(List<PassengerEntity> entities) {
        List<PassengerRS> responses = new ArrayList<>();
        for (PassengerEntity item : entities) {
            responses.add(getResponse(item));
        }

        return responses;
    }
}
