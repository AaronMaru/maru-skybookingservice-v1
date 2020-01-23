package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping;

import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking.BSegmentDRQ;
import lombok.Data;

import java.util.List;

@Data
public class OriginDestination {

    private List<BSegmentDRQ> segments;
    private String depDateTime;
    private String depCode;
    private String arrCode;

    public OriginDestination(List<BSegmentDRQ> segments, String depDateTime, String depCode, String arrCode) {
        this.segments = segments;
        this.depDateTime = depDateTime;
        this.depCode = depCode;
        this.arrCode = arrCode;
    }
}
