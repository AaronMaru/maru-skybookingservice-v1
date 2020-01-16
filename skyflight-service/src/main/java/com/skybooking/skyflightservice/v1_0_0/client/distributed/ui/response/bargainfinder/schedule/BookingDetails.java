package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDetails implements Serializable {

    @JsonProperty("classOfService")
    private String classOfService;

    @JsonProperty("mealCodeList")
    private String mealCodeList;

}
