package com.skybooking.skyflightservice.v1_0_0.service.implement.baseservice;

import com.skybooking.skyflightservice.v1_0_0.service.interfaces.baseservice.BaseServiceSV;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.http.HttpStatus;

public class BaseServiceIP implements BaseServiceSV {

    @Override
    public StructureRS responseBodyWithSuccessMessage() {
        return responseBody(HttpStatus.OK, "", null);
    }

    @Override
    public StructureRS responseBodyWithSuccessMessage(Object data) {
        return responseBody(HttpStatus.OK, "", data);
    }

    @Override
    public StructureRS responseBody(HttpStatus status) {
        return responseBody(status, null, null);
    }

    @Override
    public StructureRS responseBody(HttpStatus status, String message) {
        return responseBody(status, message, null);
    }

    public StructureRS responseBody(HttpStatus status, String message, Object data)
    {
        StructureRS structureRS = new StructureRS();
        structureRS.setStatus(status.value());
        structureRS.setMessage(message);
        structureRS.setData(data);

        return structureRS;
    }
}
