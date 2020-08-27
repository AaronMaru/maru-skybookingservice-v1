package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content.facility;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class HotelFacilityCached implements Serializable {

    private Integer hotelCode;
    private List<HotelInformationCached> details;
    private List<HotelScheduleCached> schedules;
    private List<HotelNearByDistanceCached> distances;
    private List<HotelInternetAccessCached> internets;
    private List<HotelPetCached> pets;
    private List<HotelPaymentCached> payments;
    private List<HotelMealCached> meals;
    private List<HotelPolicyCached> policies;
    private List<HotelSportCached> sports;
    private List<HotelBusinessCached> businesses;
    private List<HotelEntertainmentCached> entertainments;
    private List<HotelHealthCached> healths;
    private List<HotelKidCached> kids;
    private List<HotelServiceCached> facilities;

    public HotelFacilityCached(Integer hotelCode) {

        this.hotelCode = hotelCode;

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
