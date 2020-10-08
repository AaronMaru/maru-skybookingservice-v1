package com.skybooking.skypointservice.v1_0_0.ui.controller.limitPoint;

import com.skybooking.skypointservice.v1_0_0.service.limitPoint.SkyOwnerLimitPointSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.limitPoint.AvailablePointRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.limitPoint.SkyOwnerLimitPointRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1.0.0/limit-point")
public class LimitPointController {
    @Autowired
    private SkyOwnerLimitPointSV skyOwnerLimitPointSV;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public StructureRS createLimitPoint(@RequestBody SkyOwnerLimitPointRQ skyOwnerLimitPointRQ) {
        return skyOwnerLimitPointSV.createLimitPoint(skyOwnerLimitPointRQ);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public StructureRS updatedLimitPoint(@RequestBody SkyOwnerLimitPointRQ skyOwnerLimitPointRQ) {
        return skyOwnerLimitPointSV.updateLimitPoint(skyOwnerLimitPointRQ);
    }

    @RequestMapping(value = "detail/{limitPointId}", method = RequestMethod.POST)
    public StructureRS updatedLimitPoint(@PathVariable("limitPointId") Integer limitPointId) {
        return skyOwnerLimitPointSV.getDetailLimitPoint(limitPointId);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public StructureRS listLimitPoint() {
        return skyOwnerLimitPointSV.listLimitPoint();
    }

    @RequestMapping(value = "available/point", method = RequestMethod.GET)
    public StructureRS availablePoint(@RequestBody AvailablePointRQ availablePointRQ) {
        return skyOwnerLimitPointSV.skyStaffPointAvailable(availablePointRQ);
    }
}
