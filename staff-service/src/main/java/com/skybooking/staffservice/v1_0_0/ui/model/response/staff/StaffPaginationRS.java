package com.skybooking.staffservice.v1_0_0.ui.model.response.staff;

import java.util.List;

public class StaffPaginationRS {

    private Integer size;
    private Integer page;
    private Long totals;

    private List<StaffRS> data;


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getTotals() {
        return totals;
    }

    public void setTotals(Long totals) {
        this.totals = totals;
    }

    public List<StaffRS> getData() {
        return data;
    }

    public void setData(List<StaffRS> data) {
        this.data = data;
    }
}
