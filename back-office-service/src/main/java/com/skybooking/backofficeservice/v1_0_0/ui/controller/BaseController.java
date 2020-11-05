package com.skybooking.backofficeservice.v1_0_0.ui.controller;

import com.skybooking.backofficeservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.http.ResponseEntity;

public class BaseController {
    protected ResponseEntity<StructureRS> response(StructureRS structureRS)
    {
        return ResponseEntity
            .status(structureRS.getStatus())
            .body(structureRS);
    }
}
