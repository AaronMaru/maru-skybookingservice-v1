package com.skybooking.staffservice.v1_0_0.ui.controller.web.staff;

import com.skybooking.staffservice.v1_0_0.service.interfaces.staff.StaffSV;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.DeactiveStaffRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.response.staff.StaffPaginationRS;
import com.skybooking.staffservice.v1_0_0.util.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wv1.0.0")
public class StaffControllerW {


    @Autowired
    private StaffSV staffSV;

    @Autowired
    private Localization localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * List of staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @GetMapping("/list-staff")
    public ResponseEntity listStaff() {
        StaffPaginationRS staffs = staffSV.getStaff();
        return new ResponseEntity<>(staffs, HttpStatus.OK);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Deactive staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResponseEntity
     */
    @PatchMapping("/deactive-staff")
    public ResponseEntity deactiveStaff(@RequestBody DeactiveStaffRQ deactiveRQ) {
        staffSV.deactiveStaff(deactiveRQ);
        return new ResponseEntity<>(localization.resAPI("acc_deact", ""), HttpStatus.OK);
    }

}
