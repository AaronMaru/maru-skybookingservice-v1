package com.skybooking.staffservice.v1_0_0.util;

import com.skybooking.staffservice.exception.httpstatus.MethodNotAllowException;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff.RolePermissionTO;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff.StaffNQ;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RolePermission {


    @Autowired
    private StaffNQ staffNQ;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Check user role permission
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param role
     * @Param permission
     */
    public void checkUserRole(String role, String permission) {

        List<RolePermissionTO> rolePermission = staffNQ.findPermissionRole(role, permission);

        if (rolePermission.size() == 0) {
            throw new MethodNotAllowException("No permission on this url", "");
        }

    }

}
