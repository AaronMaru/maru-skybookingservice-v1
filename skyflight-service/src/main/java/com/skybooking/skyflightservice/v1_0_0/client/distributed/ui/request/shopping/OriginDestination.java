package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping;

import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking.BookingSegmentDRQ;
import lombok.Data;

import java.util.List;

@Data
public class OriginDestination {

    private List<BookingSegmentDRQ> segments;
    private String depDateTime;
    private String depCode;
    private String arrCode;

    public OriginDestination(List<BookingSegmentDRQ> segments, String depDateTime, String depCode, String arrCode) {
        this.segments = segments;
        this.depDateTime = depDateTime;
        this.depCode = depCode;
        this.arrCode = arrCode;
    }
}
