package com.skybooking.staffservice.v1_0_0.service.interfaces.staff;

import com.skybooking.staffservice.v1_0_0.ui.model.request.staff.DeactiveStaffRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.response.staff.StaffPaginationRS;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface StaffSV {

    StaffPaginationRS getStaff(HttpServletRequest request);

    void deactiveStaff(DeactiveStaffRQ deactiveStaffRQ);

    Object staffProfile(Long id);

}
