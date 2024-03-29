package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.CancellationDetailCached;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CancellationCached implements Serializable {
    private Boolean cancellable;
    private List<FeeCached> fees;
    private CancellationDetailCached detail;
}
