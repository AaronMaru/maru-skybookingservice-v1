package com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.cached;

import com.skybooking.skyhotelservice.constant.PaxType;
import lombok.Data;

import java.io.Serializable;

@Data
public class RatePaxCached implements Serializable {
    private Integer roomId;
    private PaxType type;
    private Integer age;
    private String name;
    private String surname;
}
