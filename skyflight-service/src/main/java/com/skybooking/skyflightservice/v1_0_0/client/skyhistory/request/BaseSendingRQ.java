package com.skybooking.skyflightservice.v1_0_0.client.skyhistory.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseSendingRQ {

    private String bookingCode;
    private String email;

}
