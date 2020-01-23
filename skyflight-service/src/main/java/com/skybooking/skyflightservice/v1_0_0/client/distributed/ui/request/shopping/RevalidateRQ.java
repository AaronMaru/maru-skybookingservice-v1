package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping;

import lombok.Data;

import java.util.List;

@Data
public class RevalidateRQ {

    private int adult;
    private int child;
    private int infant;
    private List<OriginDestination> originDestinations;

}
