package com.skybooking.skyhotelservice.v1_0_0.service.hotelsort;

import com.skybooking.skyhotelservice.constant.SortTypeConstant;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.HotelCached;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.sort.SortRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HotelSortSV {

    SortRS sortInfo(SortTypeConstant type);

    List<HotelRS> sortInfo(SortTypeConstant type, List<HotelCached> hotelCaches);

}
