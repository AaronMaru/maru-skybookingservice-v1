package com.skybooking.staffservice.v1_0_0.service.interfaces.role;

import com.skybooking.staffservice.v1_0_0.ui.model.response.role.RoleRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleSV {

    List<RoleRS> getRoles();

}
