package com.skybooking.staffservice.v1_0_0.ui.controller.web.staff;

import com.skybooking.staffservice.v1_0_0.service.interfaces.staff.StaffSV;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.DeactiveStaffRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.staffservice.v1_0_0.ui.model.response.staff.StaffPaginationRS;
import com.skybooking.staffservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wv1.0.0")
public class StaffControllerW {


    @Autowired
    private StaffSV staffSV;

    @Autowired
    private LocalizationBean localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * List of staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/list-staff")
    public ResRS listStaff(HttpServletRequest request) {
        StaffPaginationRS staffs = staffSV.getStaff(request);
        return localization.resAPI(HttpStatus.OK,"res_succ", staffs);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Staff profile
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/staff-profile/{id}")
    public ResRS staffProfile(@PathVariable Long id) {
        Object staffProfile = staffSV.staffProfile(id);
        return localization.resAPI(HttpStatus.OK,"res_succ", staffProfile);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Deactive staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @PatchMapping("/deactive-staff")
    public ResRS staffDeactive(@RequestBody DeactiveStaffRQ deactiveRQ) {
        staffSV.deactiveStaff(deactiveRQ);
        return localization.resAPI(HttpStatus.OK,"status_update", "");
    }


}
