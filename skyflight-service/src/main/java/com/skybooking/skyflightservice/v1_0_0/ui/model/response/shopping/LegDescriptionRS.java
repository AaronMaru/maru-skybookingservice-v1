package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.io.Serializable;

@Data
public class LegDescriptionRS implements Serializable {

    private String leg;
    private boolean status;

}
