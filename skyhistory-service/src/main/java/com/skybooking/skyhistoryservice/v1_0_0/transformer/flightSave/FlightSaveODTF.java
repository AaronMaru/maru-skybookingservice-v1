package com.skybooking.skyhistoryservice.v1_0_0.transformer.flightSave;

import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.flight.FlightSaveODEntity;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.flightSave.FlightSaveODRS;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class FlightSaveODTF {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * getResponse
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param entity
     * @return response
     */
    public static FlightSaveODRS getResponse(FlightSaveODEntity entity) {

        FlightSaveODRS response = new FlightSaveODRS();
        BeanUtils.copyProperties(entity, response);

        return response;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * getResponseList
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param entities
     * @return responses
     */
    public static List<FlightSaveODRS> getResponseList(List<FlightSaveODEntity> entities) {

        List<FlightSaveODRS> responses = new ArrayList<>();

        for (FlightSaveODEntity entity : entities) {
            responses.add(getResponse(entity));
        }

        return responses;

    }
}
