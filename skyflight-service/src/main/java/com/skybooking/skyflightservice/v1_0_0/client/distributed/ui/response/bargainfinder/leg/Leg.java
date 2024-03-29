package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.leg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.schedule.Schedule;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Leg implements Serializable {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("schedules")
    private List<Schedule> schedules = new ArrayList<>();
}
