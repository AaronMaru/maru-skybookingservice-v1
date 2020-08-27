package com.skybooking.skyhotelservice.v1_0_0.service.popularcity;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface PopularcitySV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve the popular cities
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    StructureRS listing();

}
