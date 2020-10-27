package com.skybooking.backofficeservice.v1_0_0.client.model.response.structure;

import lombok.Data;

@Data
public class StructureRSDS {

    private int status;
    private String message = "";
    private Object data;

}
