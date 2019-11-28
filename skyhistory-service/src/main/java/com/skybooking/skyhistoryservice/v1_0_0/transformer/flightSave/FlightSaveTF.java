package com.skybooking.skyhistoryservice.v1_0_0.transformer.flightSave;

import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.flight.FlightSaveEntity;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.flightSave.FlightSaveRS;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class FlightSaveTF {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * getResponse
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param entity
     * @return response
     */
    public static FlightSaveRS getResponse(FlightSaveEntity entity) {

        FlightSaveRS response = new FlightSaveRS();
        BeanUtils.copyProperties(entity, response);
        response.setODInfo(FlightSaveODTF.getResponseList(entity.getFlightSaveODEntities()));

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
    public static List<FlightSaveRS> getResponseList(Page<FlightSaveEntity> entities) {

        List<FlightSaveRS> responses = new ArrayList<>();
        for (FlightSaveEntity entity : entities.getContent()) {
            responses.add(getResponse(entity));
        }

        return responses;
    }

}
