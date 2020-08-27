package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilterItemRS {

    private String title;
    private boolean status = false;

    public FilterItemRS(String title) {
        this.title = title;
    }
}
