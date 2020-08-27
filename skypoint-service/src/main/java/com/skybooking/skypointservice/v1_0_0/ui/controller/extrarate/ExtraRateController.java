package com.skybooking.skypointservice.v1_0_0.ui.controller.extrarate;

import com.skybooking.skypointservice.v1_0_0.service.extrarate.ExtraRateSV;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0.0/extra-rate")
public class ExtraRateController {

    @Autowired
    private ExtraRateSV extraRateSV;

    @GetMapping("skyuser")
    public StructureRS extraSkyUser() {
        return extraRateSV.extraSkyUser();
    }

    @GetMapping("skyowner")
    public StructureRS extraSkyOwner() {
        return extraRateSV.extraSkyOwner();
    }

    @PostMapping("skyuser")
    public StructureRS editSkyUser() {
        return extraRateSV.editSkyUser();
    }

    @PostMapping("skyowner")
    public StructureRS editSkyOwner() {
        return extraRateSV.extraSkyOwner();
    }

}
