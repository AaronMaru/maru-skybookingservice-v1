package com.skybooking.skyflightservice.v1_0_0.ui.controller;

import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.MetadataSV;
import com.skybooking.skyflightservice.v1_0_0.service.interfaces.flight.FlightInfoSV;
import com.skybooking.skyflightservice.v1_0_0.service.model.ShareFlightRS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.ShareFlightRQ;
import com.skybooking.skyflightservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0")
public class ShareFlightController {

    @Autowired
    private FlightInfoSV flightInfoSV;

    @Autowired
    @Qualifier(value = "MetadataIP")
    private MetadataSV metadataSV;

    @Autowired
    private Localization localization;

    @PostMapping("/flight/sharing")
    public Object sharingFlight(@Valid @RequestBody ShareFlightRQ shareFlightRQ) {

        try {
            InternetAddress[] emailList = InternetAddress.parse(shareFlightRQ.getEmailsTo());

            if (emailList.length > 5) {
                return localization.resAPI(HttpStatus.BAD_REQUEST, "send_email_mx", null);
            }

        } catch (AddressException e) {
            e.printStackTrace();
        }

        var authenticated = metadataSV.getUserAuthenticationMetadata();

        ShareFlightRS shareFlightRS = flightInfoSV.sharingFlight(shareFlightRQ, authenticated);


        if (shareFlightRS == null) {
            return localization.resAPI(HttpStatus.BAD_REQUEST, "not_found", null);
        }

        return localization.resAPI(HttpStatus.OK, "share_flight_succ", shareFlightRS);

    }

}
