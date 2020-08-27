package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OpenReservationElement implements Serializable {

    private String elementId;
    private int id;
    @JsonProperty("ServiceRequest")
    private ServiceRequest serviceRequest;
    private String type;
    @JsonProperty("NameAssociation")
    private NameAssociation nameAssociation;
    @JsonProperty("SegmentAssociation")
    private SegmentAssociation segmentAssociation;
    @JsonProperty("FormOfPayment")
    private FormOfPayment formOfPayment;
    private int displayIndex;

}
