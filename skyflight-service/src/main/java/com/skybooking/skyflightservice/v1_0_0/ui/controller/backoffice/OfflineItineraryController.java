package com.skybooking.skyflightservice.v1_0_0.ui.controller.backoffice;

import com.skybooking.skyflightservice.v1_0_0.service.interfaces.backoffice.OfflineItinerarySV;
import com.skybooking.skyflightservice.v1_0_0.ui.controller.BaseController;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.offlineitinerary.CheckItineraryRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.offlineitinerary.OfflineItineraryRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0/back-office/itinerary")
public class OfflineItineraryController extends BaseController {

    @Autowired private OfflineItinerarySV offlineItinerarySV;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * check PNR have booking or not
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param checkItineraryRQ CheckItineraryRQ
     * @return ResponseEntity
     */
    @PreAuthorize("#oauth2.hasScope('check-offline-booking-skyflight')")
    @PostMapping("check")
    public ResponseEntity<StructureRS> check(@Valid @RequestBody CheckItineraryRQ checkItineraryRQ)
    {
        return response(offlineItinerarySV.check(checkItineraryRQ));
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * create booking offline
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param offlineItineraryRQ OfflineItineraryRQ
     * @return ResponseEntity
     */
    @PreAuthorize("#oauth2.hasScope('create-offline-booking-skyflight')")
    @PostMapping("create")
    public ResponseEntity<StructureRS> create(@Valid @RequestBody OfflineItineraryRQ offlineItineraryRQ)
    {
        return response(offlineItinerarySV.create(offlineItineraryRQ));
    }
}
