package com.skybooking.staffservice.v1_0_0.ui.controller.app.role;


import com.skybooking.staffservice.v1_0_0.service.interfaces.role.RoleSV;
import com.skybooking.staffservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.staffservice.v1_0_0.ui.model.response.role.RoleRS;
import com.skybooking.staffservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mv1.0.0")
public class RoleControllerM {

    @Autowired
    private RoleSV roleSV;

    @Autowired
    private Localization localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Find skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/roles")
    public ResRS findSkyuser() {
        List<RoleRS> roles = roleSV.getRoles();
        return localization.resAPI(HttpStatus.OK,"res_succ", roles);
    }

}
