package com.skybooking.backofficeservice.v1_0_0.service.search;

import com.skybooking.backofficeservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface DetailSearchSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Search detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param keyword string
     * @return List
     */
    StructureRS searchData(String keyword);
}
