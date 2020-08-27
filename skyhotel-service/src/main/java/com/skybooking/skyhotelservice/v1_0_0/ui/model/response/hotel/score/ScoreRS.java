package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.score;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScoreRS extends ScoreItemRS {

    private Integer reviewCount = 0;
    private List<ScoreItemRS> subScore = new ArrayList<>();

}
