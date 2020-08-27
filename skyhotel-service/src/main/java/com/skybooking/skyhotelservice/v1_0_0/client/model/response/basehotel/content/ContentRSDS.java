package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.content;

import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.content.facility.HotelFacilityRSDS;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ContentRSDS {

    private Integer code;
    private Boolean isFavorite = false;
    private BasicRSDS basic;
    private List<BoardRSDS> boards = new ArrayList<>();
    private List<PhoneRSDS> phones = new ArrayList<>();
    private LocationRSDS destination;
    private List<InterestPointRSDS> interestPoints = new ArrayList<>();
    private List<RoomRSDS> rooms = new ArrayList<>();
    private List<SegmentRSDS> segments = new ArrayList<>();
    private HotelFacilityRSDS facility;
    private List<ImageRSDS> images = new ArrayList<>();

}
