package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.room;

import lombok.Data;

@Data
public class CancellationPolicyRSDS {
    private String amount;
    private Number percent;
    private String from;
    private Number numberOfNights;
}
