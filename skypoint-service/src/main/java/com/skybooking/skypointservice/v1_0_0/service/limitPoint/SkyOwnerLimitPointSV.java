package com.skybooking.skypointservice.v1_0_0.service.limitPoint;

import com.skybooking.skypointservice.v1_0_0.ui.model.request.limitPoint.AvailablePointRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.limitPoint.SkyOwnerLimitPointRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface SkyOwnerLimitPointSV {
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Frontend: Create limit point for skyStaff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param skyOwnerLimitPointRQ SkyOwnerLimitPointRQ
     * @return StructureRS
     */
    StructureRS createLimitPoint(SkyOwnerLimitPointRQ skyOwnerLimitPointRQ);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Fronted: Update limit point for skyStaff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param skyOwnerLimitPointRQ SkyOwnerLimitPointRQ
     * @return StructureRS
     */
    StructureRS updateLimitPoint(SkyOwnerLimitPointRQ skyOwnerLimitPointRQ);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Frontend: Get limit point detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param pointLimitId Integer
     * @return StructureRS
     */
    StructureRS getDetailLimitPoint(Integer pointLimitId);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Frontend: Get limit point list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return StructureRS
     */
    StructureRS listLimitPoint();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Frontend: Get sky point available for skyStaff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return StructureRS
     */
    StructureRS skyStaffPointAvailable(AvailablePointRQ availablePointRQ);

}
