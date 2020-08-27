package com.skybooking.skypointservice.v1_0_0.service.extrarate;

import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface ExtraRateSV {

    StructureRS extraSkyUser();

    StructureRS extraSkyOwner();

    StructureRS editSkyUser();

    StructureRS editSkyOwner();
}
