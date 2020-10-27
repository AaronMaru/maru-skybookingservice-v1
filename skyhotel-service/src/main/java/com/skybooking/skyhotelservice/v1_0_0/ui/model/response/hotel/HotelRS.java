package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.*;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.facility.HotelFacilityRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.policy.PolicyRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.score.ScoreRS;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private List<AmenityRS> amenities = new ArrayList<>();
    private List<InterestPointRS> interestPoints = new ArrayList<>();
    private HotelFacilityRS facility;
    private List<RoomRS> rooms = new ArrayList<>();
    private List<SegmentRS> segments = new ArrayList<>();
    private PolicyRS policy;
    private SpecialOfferRS offer;
    private ScoreRS score = new ScoreRS();
    private PriceUnitRS priceUnit;
    private Boolean hasPromotion;
    private CancellationDetailRS cancellation;

//    public HotelRS() {
//        String amenitiesJson = "[" +
//            "    {" +
//            "        \"code\": \"PICKUP\"," +
//            "        \"name\": \"Airport pickup service\"," +
//            "        \"icon\": \"ic_pickup\"," +
//            "        \"keyword\": \"pickup, airport service\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"WIFI\"," +
//            "        \"name\": \"Wi-Fi in designated areas\"," +
//            "        \"icon\": \"ic_wifi\"," +
//            "        \"keyword\": \"wifi, internet, network\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"OUTDOOR_SWIMMING\"," +
//            "        \"name\": \"Outdoor swimming pool\"," +
//            "        \"icon\": \"ic_swimming\"," +
//            "        \"keyword\": \"swimming, pool\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"INDOOR_SWIMMING\"," +
//            "        \"name\": \"Indoor swimming pool\"," +
//            "        \"icon\": \"ic_swimming\"," +
//            "        \"keyword\": \"swimming, pool\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"SMOKE_FREE\"," +
//            "        \"name\": \"Non-smoking rooms\"," +
//            "        \"icon\": \"ic_no_smoke\"," +
//            "        \"keyword\": \"smoke, no smoke, non smoke\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"SHUTTLE_BUS\"," +
//            "        \"name\": \"Airport Shuttle Service\"," +
//            "        \"icon\": \"ic_shuttle_bus\"," +
//            "        \"keyword\": \"bus, shuttle, airport\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"24H_SERVICE\"," +
//            "        \"name\": \"Front desk (24 hours)\"," +
//            "        \"icon\": \"ic_24h_service\"," +
//            "        \"keyword\": \"24h, service, front desk, 24 hours\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"SAUNA\"," +
//            "        \"name\": \"Sauna\"," +
//            "        \"icon\": \"ic_sauna\"," +
//            "        \"keyword\": \"sauna, steam\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"BAGGAGE\"," +
//            "        \"name\": \"Luggage storage\"," +
//            "        \"icon\": \"ic_baggage\"," +
//            "        \"keyword\": \"baggage, luggage, storage\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"RESTAURANT\"," +
//            "        \"name\": \"Restaurant\"," +
//            "        \"icon\": \"ic_restaurant\"," +
//            "        \"keyword\": \"restaurant, conteen, breakfast, lunch\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"SPA\"," +
//            "        \"name\": \"Spa\"," +
//            "        \"icon\": \"ic_spa\"," +
//            "        \"keyword\": \"spa\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"PARKING\"," +
//            "        \"name\": \"Parking\"," +
//            "        \"icon\": \"ic_parking\"," +
//            "        \"keyword\": \"parking\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"SMOKE\"," +
//            "        \"name\": \"Designated Smoking Area\"," +
//            "        \"icon\": \"ic_smoke\"," +
//            "        \"keyword\": \"smoke, smoking, smoking area, smoking place\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"CONVERSION\"," +
//            "        \"name\": \"Currency exchange\"," +
//            "        \"icon\": \"ic_conversion_line\"," +
//            "        \"keyword\": \"conversion, exchange, conversion line\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"GYM\"," +
//            "        \"name\": \"Gym\"," +
//            "        \"icon\": \"ic_gym\"," +
//            "        \"keyword\": \"gym\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"PETS\"," +
//            "        \"name\": \"Pets allowed\"," +
//            "        \"icon\": \"ic_pets\"," +
//            "        \"keyword\": \"pet, pets, pets allowed\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"CAR_RENTAL\"," +
//            "        \"name\": \"Car rental\"," +
//            "        \"icon\": \"ic_car_rental\"," +
//            "        \"keyword\": \"car, car rental\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"GOLF\"," +
//            "        \"name\": \"Golf course\"," +
//            "        \"icon\": \"ic_golf\"," +
//            "        \"keyword\": \"golf, golf course\"" +
//            "    }," +
//            "    {" +
//            "        \"code\": \"GOLF\"," +
//            "        \"name\": \"Golf course\"," +
//            "        \"icon\": \"ic_golf\"," +
//            "        \"keyword\": \"golf, golf course\"" +
//            "    }" +
//            "]";
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            this.amenities = objectMapper.readValue(amenitiesJson, new TypeReference<List<AmenityRS>>(){});
//        } catch (IOException ignored) { }
//    }
}
