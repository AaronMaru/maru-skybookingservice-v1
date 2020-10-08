package com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolderDSRQ {
    private String name;
    private String surname;
    private String email;
    private String phoneCode;
    private String phoneNumber;
}
