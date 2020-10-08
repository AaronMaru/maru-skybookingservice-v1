package com.skybooking.skypointservice.v1_0_0.ui.controller.extrarate;

import com.skybooking.skypointservice.v1_0_0.service.extrarate.ExtraRateSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0.0/extra-rate")
public class ExtraRateController {

    @Autowired
    private ExtraRateSV extraRateSV;

    @RequestMapping(value = "skyuser", method = RequestMethod.GET)
    public StructureRS extraSkyUser() {
        return extraRateSV.extraSkyUser();
    }

    @RequestMapping(value = "skyowner", method = RequestMethod.GET)
    public StructureRS extraSkyOwner() {
        return extraRateSV.extraSkyOwner();
    }

    @RequestMapping(value = "skyuser", method = RequestMethod.POST)
    public StructureRS editSkyUser() {
        return extraRateSV.editSkyUser();
    }

    @RequestMapping(value = "skyowner", method = RequestMethod.POST)
    public StructureRS editSkyOwner() {
        return extraRateSV.extraSkyOwner();
    }

}
