package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.*;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.facility.HotelFacilityRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.policy.PolicyRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.score.ScoreRS;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelRS {

    private Integer code;
    private Boolean isFavorite = false;
    private RateRS.PaymentType paymentType;
    private ResourceRS resource;
    private BasicRS basic;
    private List<BoardRS> boards = new ArrayList<>();
    private List<PhoneRS> phones = new ArrayList<>();
    private LocationRS location;
    private List<ImageRS> images = new ArrayList<>();
    private List<InterestPointRS> interestPoints = new ArrayList<>();
//    private HotelFacilityRS facility;
    private List<FacilityRS> facilities;
    private List<RoomRS> rooms = new ArrayList<>();
    private List<SegmentRS> segments = new ArrayList<>();
    private PolicyRS policy;
    private SpecialOfferRS offer;
    private ScoreRS score = new ScoreRS();
    private PriceUnitRS priceUnit;
    private Boolean hasPromotion;
    private CancellationDetailRS cancellation;
}
