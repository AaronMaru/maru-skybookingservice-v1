package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ImageListRS {

    private String thumbnailUrl;
    private String typeCode;
    private String typeName;
    private Integer sortOrder;
    private String roomCode;
    private String roomType;
    private String characteristicCode;

}
