package com.skybooking.skyhistoryservice.v1_0_0.service.implment.flightSave;

import com.skybooking.skyhistoryservice.exception.httpstatus.BadRequestException;
import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.flight.FlightSaveEntity;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.saveFlight.SaveFlightNQ;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.saveFlight.SaveFlightODTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.saveFlight.SaveFlightTO;
import com.skybooking.skyhistoryservice.v1_0_0.io.repository.flight.FlightSaveRP;
import com.skybooking.skyhistoryservice.v1_0_0.service.interfaces.flightSave.FlightSaveSV;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.flightSave.FlightSaveODRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.flightSave.FlightSavePaginationRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.flightSave.FlightSaveRS;
import com.skybooking.skyhistoryservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhistoryservice.v1_0_0.util.datetime.DateTimeBean;
import com.skybooking.skyhistoryservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightSaveIP implements FlightSaveSV {

    @Autowired
    private FlightSaveRP flightSaveRP;

    @Autowired
    private SaveFlightNQ saveFlightNQ;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private DateTimeBean dateTimeBean;

    @Autowired
    private HeaderBean headerBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get list item
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param keyword
     * @param page
     * @param size
     * @return List<FlightSaveRS>
     */
    @Override
    public FlightSavePaginationRS saveFlights(String keyword, int page, int size) {

        Long skyuserId = jwtUtils.getClaim("stakeholderId", Long.class);
        Long companyId = jwtUtils.getClaim("companyId", Long.class);

        String stake = headerBean.getCompanyId(companyId);
//        String role = (stake.equals("company")) ? jwtUtils.getClaim("userRole", String.class) : "";

        String action = !keyword.equals("") ? "search" : "";
        Page<SaveFlightTO> saveFlightTOS = saveFlightNQ.savedFlight(skyuserId, companyId, action, keyword, stake, PageRequest.of(page - 1, size));

        List<FlightSaveRS> saveFlies = new ArrayList<>();
        saveFlightTOS.forEach(dataTO -> {
            FlightSaveRS saveFly = new FlightSaveRS();
            BeanUtils.copyProperties(dataTO, saveFly);

            List<FlightSaveODRS> sflightODs = saveFlightsSegment((long) dataTO.getId());
            saveFly.setODInfo(sflightODs);

            saveFlies.add(saveFly);
        });

        FlightSavePaginationRS flightSavePaginationRS = new FlightSavePaginationRS();
        flightSavePaginationRS.setSize(size);
        flightSavePaginationRS.setPage(page);
        flightSavePaginationRS.setTotals(saveFlightTOS.getTotalElements());
        flightSavePaginationRS.setData(saveFlies);

        return flightSavePaginationRS;

    }


    private List<FlightSaveODRS> saveFlightsSegment(Long flightId) {

        List<SaveFlightODTO> saveFlightODTO = saveFlightNQ.saveFlightSegments(flightId, headerBean.getLocalizationId());

        List<FlightSaveODRS> fSaveODs = new ArrayList<>();

        saveFlightODTO.forEach(dataOD -> {
            FlightSaveODRS fSaveODR = new FlightSaveODRS();
            BeanUtils.copyProperties(dataOD, fSaveODR);

            fSaveODR.setaDateTime(dateTimeBean.convertDateTime(dataOD.getaDateTime()));
            fSaveODR.setdDateTime(dateTimeBean.convertDateTime(dataOD.getdDateTime()));

            fSaveODR.setAirlineLogo45(dataOD.getAirlineLogo45());
            fSaveODR.setAirlineLogo90(dataOD.getAirlineLogo90());

            fSaveODs.add(fSaveODR);
        });

        return fSaveODs;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Delete item by id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param id
     */
    @Override
    public void deleteItem(long id) {

        Long skyuserId = jwtUtils.getClaim("stakeholderId", Long.class);
        Long companyId = jwtUtils.getClaim("companyId", Long.class);
        String stake = headerBean.getCompanyId(companyId);

        FlightSaveEntity entity = flightSaveRP.findFirstByIdAndUserId(id, Integer.parseInt(skyuserId.toString()));

        if (stake.equals("company")) {
            entity = flightSaveRP.findFirstByIdAndCompanyId(id, companyId.intValue());
        }

        if (entity == null) {
            throw new BadRequestException("not_found", null);
        }

        flightSaveRP.delete(entity);

    }

}
