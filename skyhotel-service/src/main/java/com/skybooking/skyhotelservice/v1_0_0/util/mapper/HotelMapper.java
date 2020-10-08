package com.skybooking.skyhotelservice.v1_0_0.util.mapper;

import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.ResourceRSDS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.HotelCached;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.HotelWrapperCached;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.ResourceCached;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelWrapperRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.ListHotelItemRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.ResourceRS;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface HotelMapper {

    ListHotelItemRS toListHotelItemRS(HotelRS hotelRS);

    HotelWrapperCached toHotelWrapperCached(HotelWrapperRS<HotelRS> hotelWrapperRS);
    HotelCached toHotelCached(HotelRS hotelRS);
    ResourceCached toResourceCached(ResourceRSDS resourceRSDS);
    ResourceRS toResourceRS(ResourceCached resourceCached);
    HotelRS toHotelRS(HotelCached hotelCached);

}
