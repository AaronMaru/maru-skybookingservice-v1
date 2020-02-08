package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking;

import lombok.Data;

@Data
public class BookingContactDRQ {

    private String name;
    private String email;
    private String phoneCode;
    private String phoneNumber;

}
