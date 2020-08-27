package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.content.facility;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class HotelFacilityRSDS {

    private Integer hotelCode;
    private List<HotelInformationRSDS> details;
    private List<HotelScheduleRSDS> schedules;
    private List<HotelNearByDistanceRSDS> distances;
    private List<HotelInternetAccessRSDS> internets;
    private List<HotelPetRSDS> pets;
    private List<HotelPaymentRSDS> payments;
    private List<HotelMealRSDS> meals;
    private List<HotelPolicyRSDS> policies;
    private List<HotelSportRSDS> sports;
    private List<HotelBusinessRSDS> businesses;
    private List<HotelEntertainmentRSDS> entertainments;
    private List<HotelHealthRSDS> healths;
    private List<HotelKidRSDS> kids;
    private List<HotelServiceRSDS> facilities;

    public HotelFacilityRSDS(Integer hotelCode) {

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
