package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached;

import com.skybooking.skyhotelservice.constant.CancellationTypeConstant;
import lombok.Data;

import java.io.Serializable;

@Data
public class CancellationDetailCached implements Serializable {
    private CancellationTypeConstant type = CancellationTypeConstant.FREE;
    private String name = CancellationTypeConstant.FREE.getValue();
    private String before;
}
