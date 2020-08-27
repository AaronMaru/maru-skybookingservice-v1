package com.skybooking.skyhotelservice.v1_0_0.service.viewedhotel;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

public interface ViewedHotelSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * retrieve hotels user have viewed
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    StructureRS listing();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * delete viewed hotel of user
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    StructureRS delete();

}
