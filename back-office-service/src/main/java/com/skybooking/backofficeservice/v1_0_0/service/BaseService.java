package com.skybooking.backofficeservice.v1_0_0.service;

import com.skybooking.backofficeservice.v1_0_0.ui.model.PagingRS;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.http.HttpStatus;

public interface BaseService {
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * response body follow base structure response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    StructureRS responseBodyWithSuccessMessage();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * response body follow base structure response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param data
     * @return
     */
    StructureRS responseBodyWithSuccessMessage(Object data);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * response body follow base structure response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param data
     * @param paging
     * @return
     */
    StructureRS responseBodyWithSuccessMessage(Object data, PagingRS paging);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * response body follow base structure response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param status
     * @return
     */
    StructureRS responseBody(HttpStatus status);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * response body follow base structure response
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param status
     * @param message
     * @return
     */
    StructureRS responseBody(HttpStatus status, String message);

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
    StructureRS responseBody(HttpStatus status, String message, Object data);

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
    StructureRS responseBody(HttpStatus status, String message, Object data, PagingRS paging);
}
