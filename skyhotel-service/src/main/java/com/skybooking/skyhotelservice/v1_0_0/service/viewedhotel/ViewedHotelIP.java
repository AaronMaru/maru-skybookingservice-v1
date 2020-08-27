package com.skybooking.skyhotelservice.v1_0_0.service.viewedhotel;

import com.skybooking.skyhotelservice.v1_0_0.client.action.hotel.AvailabilityAction;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.viewedhotel.HotelViewedEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.viewedHotel.ViewedHotelRP;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.service.availability.AvailabilitySV;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelconvertor.HotelConverterSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhotelservice.v1_0_0.util.header.HeaderCM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewedHotelIP extends BaseServiceIP implements ViewedHotelSV {

    @Autowired
    private JwtUtils jwt;

    @Autowired
    private HeaderCM headerCM;

    @Autowired
    private AvailabilityAction availabilityAction;

    @Autowired
    private ViewedHotelRP viewedHotelRP;

    @Autowired
    private HotelConverterSV hotelConverterSV;

    @Autowired
    private AvailabilitySV availabilitySV;

    public ViewedHotelIP() { }

    public StructureRS listing() {
        var viewedHotels = viewedHotelRP.findBySkyuserIdAndCompanyId(jwt.userToken().getSkyuserId(), headerCM.getCompanyId());

        if (viewedHotels == null)
            return responseBodyWithSuccessMessage();

        List<Integer> hotelCode = viewedHotels
            .stream()
            .map(HotelViewedEntity::getHotelCode)
            .collect(Collectors.toList());

        List<HotelRS> hotelRS = hotelConverterSV
            .availabilities(availabilityAction
                .search(availabilitySV.getSampleRequest(hotelCode)));

        return responseBodyWithSuccessMessage(hotelRS);
    }

    public StructureRS delete() {
        var viewedHotels = viewedHotelRP.findBySkyuserIdAndCompanyId(jwt.userToken().getSkyuserId(), headerCM.getCompanyId());
        viewedHotelRP.deleteAll(viewedHotels);

        return responseBodyWithSuccessMessage();
    }

}
