package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OpenReservationElement implements Serializable {

    private String elementId;
    @JsonProperty("ServiceRequest")
    private ServiceRequest serviceRequest;
    private int id;
    private String type;
    @JsonProperty("NameAssociation")
    private NameAssociation nameAssociation;
    @JsonProperty("SegmentAssociation")
    private SegmentAssociation segmentAssociation;
    @JsonProperty("FormOfPayment")
    private FormOfPayment formOfPayment;
    private int displayIndex;

}
