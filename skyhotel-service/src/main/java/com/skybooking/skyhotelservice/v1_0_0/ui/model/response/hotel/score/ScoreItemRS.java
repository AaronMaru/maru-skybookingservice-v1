package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.score;

import lombok.Data;

@Data
public class ScoreItemRS {

    private String title = "";
    private float number = 0;

    public ScoreItemRS() {
    }

    public ScoreItemRS(String title, float number) {
        this.title = title;
        this.number = number;
    }
}
