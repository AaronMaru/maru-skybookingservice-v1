package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.location;

import com.skybooking.skyhotelservice.constant.DestinationTypeGroupConstant;
import com.skybooking.skyhotelservice.exception.anotation.NotNullIfAnotherFieldNull;
import lombok.Data;

@Data
@NotNullIfAnotherFieldNull(field = "code", ifField = "hotelCode")
public class DestinationRQ {

    private String code;
    private DestinationTypeGroupConstant group = DestinationTypeGroupConstant.DESTINATION;
    private String hotelCode;

}
