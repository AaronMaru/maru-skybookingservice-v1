package com.skybooking.skyhotelservice.v1_0_0.service.viewedhotel;

import com.skybooking.skyhotelservice.config.AppConfig;
import com.skybooking.skyhotelservice.constant.WishlistConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.action.hoteldata.HotelDataAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.hoteldata.BasicHotelDataDSRQ;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.StructureRSDS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.wishlist.WishlistHotelEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.wishlist.WishlistHotelRP;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.PagingRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.ViewedHotelRS;
import com.skybooking.skyhotelservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhotelservice.v1_0_0.util.header.HeaderCM;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ViewedHotelIP extends BaseServiceIP implements ViewedHotelSV {

    @Autowired
    private JwtUtils jwt;

    @Autowired
    private HeaderCM headerCM;

    @Autowired
    private WishlistHotelRP viewedHotelRP;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HotelDataAction hotelDataAction;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppConfig appConfig;

    public ViewedHotelIP() {
    }

    @Override
    public void saveOrUpdate(HotelRS hotelWrapperRS) {
        if (httpServletRequest.getHeader("Authorization") == null)
            return;

        var companyId = headerCM.getCompanyId();
        var skyUserId = jwt.userToken().getSkyuserId();

        Optional<WishlistHotelEntity> hotelViewedEntity = viewedHotelRP.findHotelViewedAndSave(skyUserId, companyId, hotelWrapperRS.getCode(), WishlistConstant.VIEWED_HOTEL);

        if (hotelViewedEntity.isPresent()) {
            hotelViewedEntity.get().setViewCount(hotelViewedEntity.get().getViewCount() + 1);
        } else {
            hotelViewedEntity = Optional.of(new WishlistHotelEntity());
            hotelViewedEntity.get().setStakeholderUserId(skyUserId);
            hotelViewedEntity.get().setStakeholderCompanyId(companyId);
            hotelViewedEntity.get().setHotelCode(hotelWrapperRS.getCode());
            hotelViewedEntity.get().setPrice(hotelWrapperRS.getPriceUnit().getAmount());
            hotelViewedEntity.get().setCurrency(hotelWrapperRS.getPriceUnit().getCurrency());
            hotelViewedEntity.get().setStatus(true);
            hotelViewedEntity.get().setType(WishlistConstant.VIEWED_HOTEL);
            hotelViewedEntity.get().setReview(hotelWrapperRS.getScore().getNumber());
            hotelViewedEntity.get().setReviewCount(hotelWrapperRS.getScore().getReviewCount());
        }

        viewedHotelRP.save(hotelViewedEntity.get());

    }

    public StructureRS listing() {
        var viewedHotels = viewedHotelRP.findBySkyuserIdAndCompanyId(
            jwt.userToken().getSkyuserId(),
            headerCM.getCompanyId(),
            WishlistConstant.VIEWED_HOTEL);

        if (viewedHotels == null)
            return responseBodyWithSuccessMessage();

        List<Integer> hotelCode = viewedHotels
            .stream()
            .map(WishlistHotelEntity::getHotelCode)
            .collect(Collectors.toList());

        if (hotelCode.size() <= 0)
            return responseBodyWithSuccessMessage(List.of(), new PagingRS());

        StructureRSDS basicViewedHotels = hotelDataAction.getBasicHotel(new BasicHotelDataDSRQ(hotelCode));

        List<Object> data = (List<Object>) basicViewedHotels.getData();

        Map<Integer, WishlistHotelEntity> hotelEntityMap =
            viewedHotels
                .stream()
                .collect(Collectors
                    .toMap(WishlistHotelEntity::getHotelCode, o -> o));

        List<ViewedHotelRS> viewedHotelRS = data.stream()
            .map(o -> {
                ViewedHotelRS map = modelMapper.map(o, ViewedHotelRS.class);
                modelMapper.map(hotelEntityMap.get(Integer.valueOf(map.getCode())), map);
                map.setThumbnail("http://photos.hotelbeds.com/giata/" + map.getThumbnail());
                return map;
            })
            .collect(Collectors.toList());

        return responseBodyWithSuccessMessage(viewedHotelRS);
    }

    public StructureRS delete() {
        var viewedHotels = viewedHotelRP.findBySkyuserIdAndCompanyId(jwt.userToken().getSkyuserId(), headerCM.getCompanyId(), WishlistConstant.VIEWED_HOTEL);
        viewedHotelRP.deleteAll(viewedHotels);

        return responseBodyWithSuccessMessage();
    }

}
