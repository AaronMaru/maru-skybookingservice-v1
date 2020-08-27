package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached;

import com.skybooking.skyhotelservice.constant.PaymentTypeConstant;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content.*;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content.facility.HotelFacilityCached;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.price.PriceUnitCached;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.score.ScoreCached;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class HotelCached implements Serializable {

    private Integer code;
    private String paymentType = PaymentTypeConstant.POSTPAY;
    private List<PhoneCached> phones = new ArrayList<>();
    private BasicCached basic;
    private List<BoardCached> boards = new ArrayList<>();
    private LocationCached location;
    private List<ImageCached> images = new ArrayList<>();
    private List<AmenityCached> amenities = new ArrayList<>();
    private List<InterestPointCached> interestPoints = new ArrayList<>();
    private List<RoomCached> rooms = new ArrayList<>();
    private List<SegmentCached> segments = new ArrayList<>();
    private HotelFacilityCached facility;
    private ScoreCached score;
    private PriceUnitCached priceUnit;
    private FilterInfoCached filterInfo;

}
