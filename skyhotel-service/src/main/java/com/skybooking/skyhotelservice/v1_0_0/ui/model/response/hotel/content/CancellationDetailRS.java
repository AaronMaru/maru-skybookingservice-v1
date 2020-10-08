package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import com.skybooking.skyhotelservice.constant.CancellationTypeConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancellationDetailRS {
    private CancellationTypeConstant type = CancellationTypeConstant.FREE;
    private String name = CancellationTypeConstant.FREE.getValue();
    private String before;
}
