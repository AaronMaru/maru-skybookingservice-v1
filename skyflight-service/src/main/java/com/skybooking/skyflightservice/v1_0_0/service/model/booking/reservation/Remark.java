package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Remark implements Serializable {
    private String elementId;
    private int index;
    private int id;
    private String type;
    @JsonProperty("RemarkLines")
    private RemarkLines remarkLines;
}
