package com.skybooking.skyhistoryservice.v1_0_0.ui.controller.common.flightSave;

import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.flightSave.FlightSaveSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.flightSave.FlightSavePaginationRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1.0.0")
public class FlightSaveController {

    @Autowired
    private FlightSaveSV flightSaveSV;

    @Autowired
    private LocalizationBean localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get flight saved by keyword
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    @GetMapping(path = "/flight-saved")
    public ResRS getFlightSaveList(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                   @RequestParam(name = "page", defaultValue = "1") int page,
                                   @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        FlightSavePaginationRS responses = flightSaveSV.saveFlights(keyword, page , size);

        return localization.resAPI(HttpStatus.OK,"res_succ", responses);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * delete item by id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @return response
     */
    @DeleteMapping(
            path = "/flight-saved/{id}"
    )
    public ResRS deleteById(@PathVariable Long id) {

        this.flightSaveSV.deleteItem(id);

        return localization.resAPI(HttpStatus.OK,"del_succ", "");

    }

}
