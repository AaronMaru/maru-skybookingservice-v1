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

}
