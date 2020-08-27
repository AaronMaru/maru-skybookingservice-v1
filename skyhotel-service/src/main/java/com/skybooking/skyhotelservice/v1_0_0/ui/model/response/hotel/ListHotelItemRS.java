package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.*;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.policy.PolicyRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.price.PriceUnitRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.score.ScoreRS;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListHotelItemRS {
    private Integer code;
    private Boolean isFavorite = false;
    private RateRS.PaymentType paymentType;
    private BasicRS basic;
    private List<BoardRS> boards = new ArrayList<>();
    private LocationRS location;
    private List<ImageListRS> images = new ArrayList<>();
    private List<AmenityRS> amenities = new ArrayList<>();
    private List<InterestPointRS> interestPoints = new ArrayList<>();
    private PolicyRS policy;
    private ScoreRS score;
    private PriceUnitRS priceUnit;
}
