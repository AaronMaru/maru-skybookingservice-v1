package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking;

import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BCompanyRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BContactRQ;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BPassengerRQ;
import lombok.Data;

import java.util.List;

@Data
public class BCreateDRQ {

    private BCompanyRQ company;
    private BContactRQ contact;
    private List<BPassengerRQ> passengers;
    private List<BSegmentDRQ> segments;
}
