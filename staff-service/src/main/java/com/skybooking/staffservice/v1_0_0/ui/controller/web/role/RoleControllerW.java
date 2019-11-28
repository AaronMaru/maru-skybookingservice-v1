package com.skybooking.staffservice.v1_0_0.ui.controller.web.role;


import com.skybooking.staffservice.v1_0_0.service.interfaces.role.RoleSV;
import com.skybooking.staffservice.v1_0_0.ui.model.response.role.RoleRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wv1.0.0")
public class RoleControllerW {

    @Autowired
    private RoleSV roleSV;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Find skyuser
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/roles")
    public ResponseEntity findSkyuser() {
        List<RoleRS> roles = roleSV.getRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

}
