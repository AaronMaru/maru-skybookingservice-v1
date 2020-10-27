package com.skybooking.skyhotelservice.v1_0_0.ui.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PagingRS {
    private Integer page = 1;
    private Integer size = 20;
    private Long totals = (long) 0;

    public PagingRS(Integer page, Integer size, Long totals) {
        this.page = page;
        this.size = size;
        this.totals = totals;
    }
}
