package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FilterNumberRS  extends FilterItemRS {

    private Number value;

    public FilterNumberRS(Number value, String title) {
        super(title);
        this.value = value;
    }

}
