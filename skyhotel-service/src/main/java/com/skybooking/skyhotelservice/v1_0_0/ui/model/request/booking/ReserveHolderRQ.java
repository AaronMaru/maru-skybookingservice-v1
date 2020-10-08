package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.booking;

import lombok.Data;

@Data
public class ReserveHolderRQ {
    private String name;
    private String surname;
    private String email;
    private String phoneCode;
    private String phoneNumber;
}
