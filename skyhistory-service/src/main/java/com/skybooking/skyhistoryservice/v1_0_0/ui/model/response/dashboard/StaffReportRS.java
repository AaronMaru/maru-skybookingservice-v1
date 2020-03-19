package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard;

import java.util.List;

public class StaffReportRS {

    private TotalStaffReportRS totalStaff;
    private List<StaffReportListRS> listStaff;

    public TotalStaffReportRS getTotalStaff() {
        return totalStaff;
    }

    public void setTotalStaff(TotalStaffReportRS totalStaff) {
        this.totalStaff = totalStaff;
    }

    public List<StaffReportListRS> getListStaff() {
        return listStaff;
    }

    public void setListStaff(List<StaffReportListRS> listStaff) {
        this.listStaff = listStaff;
    }

}
