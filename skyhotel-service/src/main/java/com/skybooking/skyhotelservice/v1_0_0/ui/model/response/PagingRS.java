package com.skybooking.skyhotelservice.v1_0_0.ui.model.response;

import lombok.Data;

@Data
public class PagingRS {
    private Integer page;
    private Integer size;
    private Integer totals;

    public PagingRS(Integer page, Integer size, Integer totals) {
        this.page = page;
        this.size = size;
        this.totals = totals;
    }
}
