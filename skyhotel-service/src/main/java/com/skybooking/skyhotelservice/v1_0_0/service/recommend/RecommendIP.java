package com.skybooking.skyhotelservice.v1_0_0.service.recommend;

import com.skybooking.skyhotelservice.v1_0_0.client.action.hotel.AvailabilityAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.content.GeolocationRQDS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.recentsearch.RecentSearchEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.recentsearch.RecentSearchRP;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.service.availability.AvailabilitySV;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.PromotionRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.RoomStayRS;
import com.skybooking.skyhotelservice.v1_0_0.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendIP extends BaseServiceIP implements RecommendSV {

    @Autowired
    private AvailabilitySV availabilitySV;

    @Autowired
    private RecentSearchRP recentSearchRP;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    HttpServletRequest httpServletRequest;

    public StructureRS listing(double lat, double lng)
    {

        List<String> desCode = new ArrayList<>();
        GeolocationRQDS geolocation = new GeolocationRQDS();

        if (httpServletRequest.getHeader("Authorization") != null) {
            Long companyId = (long) ConvertUtils.convert(httpServletRequest.getHeader("X-CompanyId"), Long.class);
            Long skyuserId = jwtUtils.getClaim("stakeholderId", Long.class);
            var recentSearch = recentSearchRP.listRecentSearch(skyuserId, companyId);
            desCode = recentSearch.stream().map(RecentSearchEntity::getDestinationCode).collect(Collectors.toList());
        } else {
            geolocation = new GeolocationRQDS(lat, lng);
        }

        List<HotelRS> data = availabilitySV.getAvailabilityByDesCodes(desCode, geolocation);

        data.stream().forEach(hotel -> {
            hotel.getRooms().stream().forEach(room -> {
                room.getRates().stream().forEach(rate -> {
                    if (rate.getPromotions() == null) {
                        hotel.setHasPromotion(true);
                    }
                });
            });
        });
        data = data.stream().sorted(Comparator.comparing((HotelRS hotel) -> hotel.getHasPromotion()).thenComparing(compares -> compares.getScore().getNumber(), Comparator.reverseOrder())).collect(Collectors.toList());
        return responseBodyWithSuccessMessage(data);

    }

}
