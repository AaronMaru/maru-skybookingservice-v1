package com.skybooking.skyhotelservice.v1_0_0.ui.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PagingRS {
    private Integer page = 1;
    private Integer size = 10;
    private Integer totals = 0;

    public PagingRS(Integer page, Integer size, Integer totals) {
        this.page = page;
        this.size = size;
        this.totals = totals;
    }
}
