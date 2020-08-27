package com.skybooking.skyhotelservice.v1_0_0.service;

import com.skybooking.skyhotelservice.constant.ResponseConstant;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.PagingRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceIP implements BaseService{

    @Autowired private StructureRS structureRS;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * response body follow base structure response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @Override
    public StructureRS responseBodyWithSuccessMessage() {
        return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, null, null);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * response body follow base structure response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param data
     * @return
     */
    @Override
    public StructureRS responseBodyWithSuccessMessage(Object data) {
        return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, data, null);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * response body follow base structure response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param data
     * @param paging
     * @return
     */
    @Override
    public StructureRS responseBodyWithSuccessMessage(Object data, PagingRS paging) {
        return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS, data, paging);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * response body follow base structure response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param status
     * @return
     */
    @Override
    public StructureRS responseBody(HttpStatus status) {
        return responseBody(status, null, null, null);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * response body follow base structure response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param status
     * @param message
     * @return
     */
    @Override
    public StructureRS responseBody(HttpStatus status, String message) {
        return responseBody(status, message, null, null);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * response body follow base structure response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param status
     * @param message
     * @param data
     * @return
     */
    @Override
    public StructureRS responseBody(HttpStatus status, String message, Object data) {
        return responseBody(status, message, data, null);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * response body follow base structure response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param status
     * @param message
     * @param data
     * @param paging
     * @return
     */
    public StructureRS responseBody(HttpStatus status, String message, Object data, PagingRS paging)
    {
        structureRS.setStatus(status.value());
        structureRS.setMessage(message);
        structureRS.setData(data);
        structureRS.setPaging(paging);

        return structureRS;
    }
}
