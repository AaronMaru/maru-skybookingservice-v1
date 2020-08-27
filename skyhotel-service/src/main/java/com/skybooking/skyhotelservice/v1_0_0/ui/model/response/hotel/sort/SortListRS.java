package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.sort;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SortListRS extends SortItemRS {

    private List<SortValueRS> list = new ArrayList<>();

}
