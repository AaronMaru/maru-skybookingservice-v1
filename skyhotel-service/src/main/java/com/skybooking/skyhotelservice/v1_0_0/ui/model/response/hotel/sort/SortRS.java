package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.sort;

import lombok.Data;

@Data
public class SortRS {

    private SortValueRS recommended;
    private SortListRS price;
    private SortListRS star;
    private SortValueRS guestRating;
    private SortValueRS distance;

}
