package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.InterestPointRS;
import lombok.Data;

import java.io.Serializable;

@Data
public class InterestPointCached implements Serializable {

    private Integer facilityCode;
    private Integer facilityGroupCode;
    private String name;
    private String distance;

    public InterestPointRS getInterestPoint()
    {
        InterestPointRS interestPointRS = new InterestPointRS();
        interestPointRS.setFacilityCode(getFacilityCode());
        interestPointRS.setFacilityGroupCode(getFacilityGroupCode());
        interestPointRS.setName(getName());
        interestPointRS.setDistance(getDistance());

        return interestPointRS;
    }

}
