package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content.facility;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
public class HotelScheduleCached extends BaseHotelFacilityCached {
    private String timeFrom;
    private String timeTo;
}
