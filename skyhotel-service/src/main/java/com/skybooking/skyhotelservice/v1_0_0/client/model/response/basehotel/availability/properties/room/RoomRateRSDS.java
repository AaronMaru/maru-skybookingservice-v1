package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.room;

import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room.CancellationRSDS;
import lombok.Data;

import java.util.List;

@Data
public class RoomRateRSDS {

    private String rateKey;
    private String rateType;
    private String net;
    private Integer allotment;
    private String paymentType;
    private Boolean packaging;
    private String boardCode;
    private String boardName;
    private Integer rooms;
    private Integer adults;
    private Integer children;
    private List<CancellationPolicyRSDS> cancellationPolicies;

}
