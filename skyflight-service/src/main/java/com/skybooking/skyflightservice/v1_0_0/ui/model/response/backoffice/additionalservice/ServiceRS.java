package com.skybooking.skyflightservice.v1_0_0.ui.model.response.backoffice.additionalservice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ServiceRS {

    private String type;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date happenedAt;

    private String description;
}
