package com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.flightSave;

import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.flightSave.FlightSaveRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightSaveSV {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get list item
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param keyword
     * @param page
     * @param limit
     * @param by
     * @param order
     * @return
     */
    List<FlightSaveRS> getItemsByKeyword(String keyword, int page, int limit, String by, String order);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * delete item by id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     */
    void deleteItem(long id);
}
