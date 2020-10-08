package com.skybooking.skypointservice.v1_0_0.service.extrarate;

import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface ExtraRateSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Common: Get top up extra rate of skyUser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return StructureRS
     */
    StructureRS extraSkyUser();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Common: Get top up extra rate of skyOwner
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return StructureRS
     */
    StructureRS extraSkyOwner();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Common: Update top up extra rate of skyUser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return StructureRS
     */
    StructureRS editSkyUser();


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Common: Update top up extra rate of skyOwner
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return StructureRS
     */
    StructureRS editSkyOwner();
}
