package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.score;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScoreItemCached implements Serializable {

    private String title = "";
    private float number = 0;

}
