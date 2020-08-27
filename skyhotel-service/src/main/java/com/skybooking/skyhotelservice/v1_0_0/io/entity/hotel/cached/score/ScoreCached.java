package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.score;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ScoreCached extends ScoreItemCached implements Serializable {

    private Integer reviewCount = 0;
    private List<ScoreCached> subScore = new ArrayList<>();

}
