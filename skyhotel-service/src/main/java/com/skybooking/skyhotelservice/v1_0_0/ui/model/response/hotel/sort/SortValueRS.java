package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.sort;

import com.skybooking.skyhotelservice.constant.SortTypeConstant;
import lombok.Data;

@Data
public class SortValueRS extends SortItemRS {

    private SortTypeConstant value;

}
