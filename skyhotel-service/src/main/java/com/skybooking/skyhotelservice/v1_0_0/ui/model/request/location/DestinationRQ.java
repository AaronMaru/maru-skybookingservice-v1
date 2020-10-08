package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.location;

import com.skybooking.core.validators.annotations.IsIn;
import com.skybooking.skyhotelservice.constant.DestinationTypeGroupConstant;
import com.skybooking.skyhotelservice.exception.anotation.NotNullIfAnotherFieldNull;
import lombok.Data;

@Data
@NotNullIfAnotherFieldNull(field = "code", ifField = "hotelCode")
public class DestinationRQ {

    private String code;

    @IsIn(contains = {"GEOLOCATION", "DESTINATION", "TERMINAL"})
    private String group = DestinationTypeGroupConstant.DESTINATION.name();

    private String hotelCode;

}
