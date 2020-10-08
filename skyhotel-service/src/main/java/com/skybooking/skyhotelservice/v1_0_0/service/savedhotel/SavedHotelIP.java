package com.skybooking.skyhotelservice.v1_0_0.service.savedhotel;

import com.skybooking.skyhotelservice.constant.WishlistConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.action.hoteldata.HotelDataAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.hoteldata.BasicHotelDataDSRQ;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.StructureRSDS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.wishlist.WishlistHotelEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.wishlist.WishlistHotelRP;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.service.hotelCached.HotelCachedSV;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.HotelCodeRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.PagingRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelWrapperRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.SavedHotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.ViewedHotelRS;
import com.skybooking.skyhotelservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhotelservice.v1_0_0.util.header.HeaderCM;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavedHotelIP extends BaseServiceIP implements SavedHotelSV {

    @Autowired
    private WishlistHotelRP wishlistRP;

    @Autowired
    private JwtUtils jwt;

    @Autowired
    private HeaderCM headerCM;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private HotelDataAction hotelDataAction;

    private final HotelCachedSV hotelCachedSV;

    public StructureRS listing() {
        var wishlists = wishlistRP.findBySkyuserIdAndCompanyId(
            jwt.userToken().getSkyuserId(),
            headerCM.getCompanyId(),
            WishlistConstant.SAVED_HOTEL);

        List<Integer> hotelCode =
            wishlists
                .stream()
                .map(WishlistHotelEntity::getHotelCode)
                .collect(Collectors.toList());

        if (hotelCode.size() <= 0)
            return responseBodyWithSuccessMessage(List.of(), new PagingRS());

        StructureRSDS basicHotelDataDSRS = hotelDataAction.getBasicHotel(new BasicHotelDataDSRQ(hotelCode));

        List<Object> data = (List<Object>) basicHotelDataDSRS.getData();

        Map<Integer, WishlistHotelEntity> hotelEntityMap =
            wishlists
                .stream()
                .collect(Collectors
                    .toMap(WishlistHotelEntity::getHotelCode, o -> o));

        List<SavedHotelRS> savedHotelRS = data.stream()
            .map(o -> {
                SavedHotelRS map = modelMapper.map(o, SavedHotelRS.class);
                modelMapper.map(hotelEntityMap.get(Integer.valueOf(map.getCode())), map);
                map.setThumbnail("http://photos.hotelbeds.com/giata/" + map.getThumbnail());
                return map;
            })
            .collect(Collectors.toList());

        return responseBodyWithSuccessMessage(savedHotelRS);

    }

    public StructureRS addOrUpdate(HotelCodeRQ hotelCodeRQ) {

        var wishlist = wishlistRP.findHotelViewedAndSave(jwt.userToken().getSkyuserId(),
            headerCM.getCompanyId(), hotelCodeRQ.getHotelCode(), WishlistConstant.SAVED_HOTEL).orElse(
            new WishlistHotelEntity(jwt.userToken().getSkyuserId(), headerCM.getCompanyId(), hotelCodeRQ.getHotelCode())
        );

        wishlist.setStatus(!wishlist.getStatus());

        if (wishlist.getStatus()) {
            HotelWrapperRS<HotelRS> hotelWrapperiesRS = hotelCachedSV.retrieveHotelWrapper(hotelCodeRQ.getRequestId());
            if (hotelWrapperiesRS.getHotelList().size() > 0) {
                var hotelWrapperRS = hotelWrapperiesRS.getHotelList()
                    .stream()
                    .filter(data -> data.getCode().equals(hotelCodeRQ.getHotelCode())).findFirst();
                if (hotelWrapperRS.isPresent()) {
                    wishlist.setPrice(hotelWrapperRS.get().getPriceUnit().getAmount());
                    wishlist.setCurrency(hotelWrapperRS.get().getPriceUnit().getCurrency());
                    wishlist.setReview(hotelWrapperRS.get().getScore().getNumber());
                    wishlist.setReviewCount(hotelWrapperRS.get().getScore().getReviewCount());
                }
            }
        }

        wishlist = wishlistRP.save(wishlist);

        return responseBodyWithSuccessMessage(wishlist);

    }

}
