package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.reservation.OpenReservationElementTA;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class OpenReservationElementsTA implements Serializable {
    private List<OpenReservationElementTA> openReservationElement;
}
