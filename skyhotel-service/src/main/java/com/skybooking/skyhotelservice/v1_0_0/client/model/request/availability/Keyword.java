package com.skybooking.skyhotelservice.v1_0_0.client.model.request.availability;

import lombok.Data;

import java.util.List;

@Data
public class Keyword {
    private List<Integer> keyword;
    private boolean allIncluded;
}
