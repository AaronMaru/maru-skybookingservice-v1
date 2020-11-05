package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ImageRSDS {

    private String thumbnail;
    private String typeCode;
    private String typeName;
    private Integer sortOrder;
    private String roomCode;
    private String roomType;
    private String characteristicCode;
    private Boolean service;

}
