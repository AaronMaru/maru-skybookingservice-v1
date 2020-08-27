package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.facility;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@Data
//@JsonIgnoreProperties({"hotelCode"})
public class HotelFacilityRS {

//    private Integer hotelCode;
    private List<HotelInformationRS> details;
    private List<HotelScheduleRS> schedules;
    private List<HotelNearByDistanceRS> distances;
    private List<HotelInternetAccessRS> internets;
    private List<HotelPetRS> pets;
    private List<HotelPaymentRS> payments;
    private List<HotelMealRS> meals;
    private List<HotelPolicyRS> policies;
    private List<HotelSportRS> sports;
    private List<HotelBusinessRS> businesses;
    private List<HotelEntertainmentRS> entertainments;
    private List<HotelHealthRS> healths;
    private List<HotelKidRS> kids;
    private List<HotelServiceRS> facilities;

    public HotelFacilityRS() {
    }

    public HotelFacilityRS(Integer hotelCode) {

//        this.hotelCode = hotelCode;

        details = new ArrayList<>();
        schedules = new ArrayList<>();
        distances = new ArrayList<>();
        internets = new ArrayList<>();
        pets = new ArrayList<>();
        payments = new ArrayList<>();
        meals = new ArrayList<>();
        policies = new ArrayList<>();
        sports = new ArrayList<>();
        entertainments = new ArrayList<>();
        businesses = new ArrayList<>();
        healths = new ArrayList<>();
        kids = new ArrayList<>();
        facilities = new ArrayList<>();

    }

}
