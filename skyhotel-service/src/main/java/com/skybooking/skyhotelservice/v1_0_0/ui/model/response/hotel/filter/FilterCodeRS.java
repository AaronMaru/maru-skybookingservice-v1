package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FilterCodeRS extends FilterItemRS {

    private String value;

    public FilterCodeRS(String value, String title) {
        super(title);
        this.value = value;
    }
}
