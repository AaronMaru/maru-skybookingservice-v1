package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking;

import com.skybooking.skyflightservice.config.company.Company;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BContactRQ;
import lombok.Data;

import java.util.List;

@Data
public class BCreateDRQ {

    private Company company;
    private BContactRQ contact;
    private List<BPassengerDRQ> passengers;
    private List<BSegmentDRQ> segments;

}
