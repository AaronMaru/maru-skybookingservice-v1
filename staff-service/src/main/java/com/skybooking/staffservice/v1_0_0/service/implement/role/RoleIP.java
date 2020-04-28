package com.skybooking.staffservice.v1_0_0.service.implement.role;

import com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff.RoleTO;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff.StaffNQ;
import com.skybooking.staffservice.v1_0_0.service.interfaces.role.RoleSV;
import com.skybooking.staffservice.v1_0_0.ui.model.response.role.RoleRS;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleIP implements RoleSV {

    @Autowired
    private StaffNQ roleNQ;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get roles
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return List<RoleRS>
     */
    public List<RoleRS> getRoles() {

        List<RoleTO> rolesTO = roleNQ.listOrFindRole("", "");
        List<RoleRS> rolesRS = new ArrayList<>();

        for (RoleTO roleTO : rolesTO) {
            RoleRS roleRS = new RoleRS();
            BeanUtils.copyProperties(roleTO, roleRS);

            rolesRS.add(roleRS);
        }

        return rolesRS;

    }

}
