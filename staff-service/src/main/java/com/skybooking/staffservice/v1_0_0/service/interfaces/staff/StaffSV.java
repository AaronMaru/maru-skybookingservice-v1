package com.skybooking.staffservice.v1_0_0.service.interfaces.staff;

import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.DeactiveStaffRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.SkyuserIdStaffRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.response.staff.StaffPaginationRS;
import org.springframework.stereotype.Service;

@Service
public interface StaffSV {

    StaffPaginationRS getStaff();

    void deactiveStaff(DeactiveStaffRQ deactiveStaffRQ);

    Object staffProfile(Long id);

}
