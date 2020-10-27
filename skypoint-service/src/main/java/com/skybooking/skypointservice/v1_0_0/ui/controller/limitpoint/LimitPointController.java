package com.skybooking.skypointservice.v1_0_0.ui.controller.limitpoint;

import com.skybooking.skypointservice.v1_0_0.service.limitPoint.SkyOwnerLimitPointSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.limitPoint.AvailablePointRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.limitPoint.SkyOwnerLimitPointRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0/limit-point")
public class LimitPointController {
    @Autowired
    private SkyOwnerLimitPointSV skyOwnerLimitPointSV;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public StructureRS createLimitPoint(@Valid @RequestBody SkyOwnerLimitPointRQ skyOwnerLimitPointRQ) {
        return skyOwnerLimitPointSV.createLimitPoint(skyOwnerLimitPointRQ);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public StructureRS updatedLimitPoint(@RequestBody SkyOwnerLimitPointRQ skyOwnerLimitPointRQ) {
        return skyOwnerLimitPointSV.updateLimitPoint(skyOwnerLimitPointRQ);
    }

    @RequestMapping(value = "detail/{stakeholderUserId}", method = RequestMethod.POST)
    public StructureRS getDetailLimitPoint(@PathVariable("stakeholderUserId") Integer stakeholderUserId) {
        return skyOwnerLimitPointSV.getDetailLimitPoint(stakeholderUserId);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public StructureRS listLimitPoint() {
        return skyOwnerLimitPointSV.listLimitPoint();
    }

    @RequestMapping(value = "available/point", method = RequestMethod.POST)
    public StructureRS availablePoint(@Valid @RequestBody AvailablePointRQ availablePointRQ) {
        return skyOwnerLimitPointSV.skyStaffPointAvailable(availablePointRQ);
    }
}
