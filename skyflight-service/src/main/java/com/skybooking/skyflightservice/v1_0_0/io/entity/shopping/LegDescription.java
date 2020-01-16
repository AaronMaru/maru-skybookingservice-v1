package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;

@Data
public class LegDescription implements Serializable {
    private boolean status;
    private String leg;
}
