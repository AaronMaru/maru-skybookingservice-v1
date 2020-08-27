package com.skybooking.skyhotelservice.v1_0_0.service.savedhotel;

import com.skybooking.skyhotelservice.exception.httpstatus.BadRequestException;
import com.skybooking.skyhotelservice.v1_0_0.client.action.hotel.AvailabilityAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.availability.AvailabilityRSDS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.savedhotel.SavedHotelEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.wishlist.WishlistHotelRP;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.service.availability.AvailabilitySV;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelconvertor.HotelConverterSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.HotelCodeRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhotelservice.v1_0_0.util.header.HeaderCM;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavedHotelIP extends BaseServiceIP implements SavedHotelSV {

    @Autowired
    private WishlistHotelRP wishlistRP;

    @Autowired
    private JwtUtils jwt;

    @Autowired
    private HeaderCM headerCM;

    @Autowired
    private HotelConverterSV hotelConverterSV;

    @Autowired
    private AvailabilityAction availabilityAction;

    @Autowired private AvailabilitySV availabilitySV;

    @Autowired
    ModelMapper modelMapper;

    public StructureRS listing()
    {
        var wishlists = wishlistRP.findBySkyuserIdAndCompanyId(jwt.userToken().getSkyuserId(), headerCM.getCompanyId());
        List<Integer> hotelCode = wishlists.stream().map(SavedHotelEntity::getHotelCode).collect(Collectors.toList());

        AvailabilityRSDS availabilityRSDS = new AvailabilityRSDS();
        if (hotelCode.size() > 0)
            availabilityRSDS = availabilityAction.search(availabilitySV.getSampleRequest(hotelCode));

        if (availabilityRSDS == null)
            return responseBodyWithSuccessMessage();

        List<HotelRS> hotelRS = hotelConverterSV.availabilities(availabilityRSDS);

        return responseBodyWithSuccessMessage(hotelRS);

    }

    public StructureRS addOrUpdate(HotelCodeRQ hotelCodeRQ)
    {
        var wishlist = wishlistRP.findBySkyuserIdAndCompanyIdAndHotelCode(jwt.userToken().getSkyuserId(),
            headerCM.getCompanyId(), hotelCodeRQ.getHotelCode()).orElse(
            new SavedHotelEntity(jwt.userToken().getSkyuserId(), headerCM.getCompanyId(), hotelCodeRQ.getHotelCode())
        );

        if (wishlist.getId() == null) {
            List<HotelRS> hotelRS = hotelConverterSV
                .availabilities(availabilityAction
                    .search(availabilitySV.getSampleRequest(List.of(wishlist.getHotelCode()))));
            if (hotelRS.size() == 0) {
                throw new BadRequestException("Please provide the valid hotel code", null);
            }
        }

        wishlist.setStatus(!wishlist.getStatus());
        wishlist = wishlistRP.save(wishlist);

        return responseBodyWithSuccessMessage(wishlist);
    }

}
