package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class HiddenStop implements Serializable {

    @JsonProperty("Meal")
    public Meal meal;
    @JsonProperty("DepartureDateTime")
    public String departureDateTime;
    @JsonProperty("ArrivalDateTime")
    public String arrivalDateTime;
    @JsonProperty("Airport")
    public String airport;
    @JsonProperty("EquipmentType")
    public String equipmentType;

}
