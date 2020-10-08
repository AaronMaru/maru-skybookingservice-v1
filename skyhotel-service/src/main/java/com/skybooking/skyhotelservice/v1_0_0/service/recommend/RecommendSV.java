package com.skybooking.skyhotelservice.v1_0_0.service.recommend;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface RecommendSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve location hotels
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    StructureRS listing(double lat, double lng);

}
