package com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.cached;

import lombok.Data;

import java.io.Serializable;

@Data
public class HolderCached implements Serializable {
    private String name;
    private String surname;
    private String email;
    private String phoneCode;
    private String phoneNumber;
}
