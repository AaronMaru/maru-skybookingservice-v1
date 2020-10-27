package com.skybooking.skyhotelservice.constant;

public class EndpointConstant {

    public static class Auth {
        public static final String V1_0_0 = "/secure/v1.0.0/oauth/token";
        public static final String V1_0_1 = "/secure/v1.0.1/oauth/token";
    }

    public static class QuickSearchDestination {
        public static final String V1_0_0 = "hotel-content/v1.0.0/destination/quick-search";
    }

    public static class AutocompleteSearchDestination {
        public static final String V1_0_0 = "hotel-content/v1.0.0/destination/autocomplete-search";
    }

    public static class Availability {
        public static final String V1_0_0 = "hotel-content/v1.0.0/hotels";
    }

    public static class DataHotel {
        public static final String V1_0_0 = "hotel-content/v1.0.0/datahotels";
        public static final String V1_0_0_BASIC = "hotel-content/v1.0.0/datahotels/basic";
    }

    public static class SimilarHotel {
        public static final String V1_0_0 = "hotel-content/v1.0.0/similar-hotels";
    }

    public static class RecommendHotel {
        public static final String V1_0_0 = "hotel-content/v1.0.0/location-hotel";
    }
    public static class DestinationHotelCode {
        public static final String V1_0_0 = "hotel-content/v1.0.0/hotels/codes";
    }

    public static class ViewedHotel {
        public static final String V1_0_0 = "hotel-content/v1.0.0/viewed-hotel";
    }

    public static class PopularCity {
        public static final String V1_0_0 = "hotel-content/v1.0.0/popular-city";
    }

    public static class CheckRate {
        public static final String V1_0_0 = "hotel/v1.0.0/checkrates";
    }

    public static class Reserve {
        public static final String V1_0_0 = "hotel/v1.0.0/reserve";
    }

    public static class Booking {
        public static final String V1_0_0 = "hotel/v1.0.0/bookings";
    }

    public static class BookingHistory {
        public static final String V1_0_0 = "hotel/v1.0.0/histories";
    }

    public static class PaymentSuccess {
        public static final String V1_0_0 = "event/v1.0.0/email/payment-success";
    }
    public static class PaymentInfo {
        public static final String V1_0_0 = "event/v1.0.0/email/payment-info";
    }

}
