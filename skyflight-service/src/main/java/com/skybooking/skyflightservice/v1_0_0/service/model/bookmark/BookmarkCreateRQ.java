package com.skybooking.skyflightservice.v1_0_0.service.model.bookmark;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookmarkCreateRQ {
    private String request;
    private String itinerary;
}
