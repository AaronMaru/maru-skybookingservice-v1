package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.schedule;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleComponent implements Serializable {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("bookingDetails")
    private BookingDetails bookingDetails;

    @JsonProperty("carrier")
    private Carrier carrier;

    @JsonProperty("arrival")
    private Arrival arrival;

    @JsonProperty("departure")
    private Departure departure;

    @JsonProperty("stopCount")
    private Integer stopCount;

    @JsonProperty("eTicketable")
    private Boolean eTicketable;

    @JsonProperty("flightStatused")
    private Integer flightStatused;

    @JsonProperty("dotRating")
    private String dotRating;

    @JsonProperty("frequency")
    private String frequency;

    @JsonProperty("funnel")
    private Boolean funnel;

    @JsonProperty("governmentApproval")
    private Boolean governmentApproval;

    @JsonProperty("hiddenStops")
    private List<HiddenStop> hiddenStops = new ArrayList<>();

    @JsonProperty("onTimePerformance")
    private Integer onTimePerformance;

    @JsonProperty("smokingAllowed")
    private Boolean smokingAllowed;

    @JsonProperty("totalMilesFlown")
    private Integer totalMilesFlown;

    @JsonProperty("trafficRestriction")
    private String trafficRestriction;

    @JsonProperty("message")
    private String message;

    @JsonProperty("messageType")
    private String messageType;

}
