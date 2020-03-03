package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.flightSave;

import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.BookingRS;

import java.util.List;

public class FlightSavePaginationRS {

    private Integer size;
    private Integer page;
    private Long totals;

    private List<FlightSaveRS> data;

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

    public List<FlightSaveRS> getData() {
        return data;
    }

    public void setData(List<FlightSaveRS> data) {
        this.data = data;
    }
}
