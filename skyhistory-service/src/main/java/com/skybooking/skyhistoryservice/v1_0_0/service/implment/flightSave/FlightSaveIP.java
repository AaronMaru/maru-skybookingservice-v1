package com.skybooking.skyhistoryservice.v1_0_0.service.implment.flightSave;

import com.skybooking.skyhistoryservice.exception.httpstatus.BadRequestException;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.flight.FlightSaveEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.flight.FlightSaveODEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.flight.FlightSaveODRP;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.flight.FlightSaveRP;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.flightSave.FlightSaveSV;
import com.skybooking.skyhistoryservice.v1_0_0.transformer.flightSave.FlightSaveTF;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.flightSave.FlightSaveRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.flight.FlightSaveODSUtils;
import com.skybooking.skyhistoryservice.v1_0_0.util.flight.FlightSaveSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightSaveIP implements FlightSaveSV {

    @Autowired
    private FlightSaveRP flightSaveRP;

    @Autowired
    private FlightSaveODRP flightSaveODRP;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get flight save by keyword
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param keyword
     * @param page
     * @param limit
     * @param by
     * @param order
     * @return list
     */
    @Override
    public List<FlightSaveRS> getItemsByKeyword(String keyword, int page, int limit, String by, String order) {

        long userId = 65;

        Page<FlightSaveODEntity> flightSaveODEntities = flightSaveODRP.findAllByUserAndKeyword(userId, keyword, PageRequest.of(0, Integer.MAX_VALUE, FlightSaveODSUtils.sort(by, order)));

        List<Long> ids = new ArrayList<>();

        for (FlightSaveODEntity entity : flightSaveODEntities.getContent()) {
            Long id = entity.getFlightSaveEntity().getId();
            ids.add(id);
        }

        if (!ids.isEmpty())
            return FlightSaveTF.getResponseList(flightSaveRP.findAllByUserAndKeywordInIds(userId, keyword, ids, PageRequest.of(page, limit, FlightSaveSUtils.sort(by, order))));

        return FlightSaveTF.getResponseList(flightSaveRP.findAllByUserAndKeyword(userId, keyword, PageRequest.of(page, limit, FlightSaveSUtils.sort(by, order))));

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * delete item by id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     */
    @Override
    public void deleteItem(long id) {

        FlightSaveEntity entity = flightSaveRP.findFirstByIdAndUserId(id, 65);

        if (entity == null) {
            throw new BadRequestException("not_found", null);
        }

        flightSaveRP.delete(entity);
    }
}
