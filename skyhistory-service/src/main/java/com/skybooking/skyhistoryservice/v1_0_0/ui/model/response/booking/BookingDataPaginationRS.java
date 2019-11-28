package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking;

import java.util.List;

public class BookingDataPaginationRS {

    private Integer size;
    private Integer page;
    private Long totals;

    private List<BookingRS> data;


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

    public List<BookingRS> getData() {
        return data;
    }

    public void setData(List<BookingRS> data) {
        this.data = data;
    }
}
