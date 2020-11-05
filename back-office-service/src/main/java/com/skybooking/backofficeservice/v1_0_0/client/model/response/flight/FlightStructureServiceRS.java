package com.skybooking.backofficeservice.v1_0_0.client.model.response.flight;

import com.skybooking.backofficeservice.v1_0_0.client.model.response.StructureServiceRS;
import lombok.Data;

@Data
public class FlightStructureServiceRS extends StructureServiceRS {
    private FlightDetailServiceRS data;
}
