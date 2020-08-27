package com.skybooking.skyhotelservice.v1_0_0.service.recommend;

import com.skybooking.skyhotelservice.constant.ResponseConstant;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.recentsearch.RecentSearchEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.recentsearch.RecentSearchRP;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.service.availability.AvailabilitySV;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendIP extends BaseServiceIP implements RecommendSV {

    @Autowired
    private AvailabilitySV availabilitySV;

    @Autowired
    private RecentSearchRP recentSearchRP;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    HttpServletRequest httpServletRequest;

    public StructureRS listing()
    {
        List<RecentSearchEntity> recentSearchList = null;

        if (httpServletRequest.getHeader("Authorization") != null) {

            int companyId = jwtUtils.getClaim("companyId", Integer.class);
            int userId = jwtUtils.getClaim("stakeholderId", Integer.class);
            recentSearchList = recentSearchRP.listRecentSearch(userId, companyId);

        }

        if (recentSearchList == null) {
            return responseBody(HttpStatus.OK, ResponseConstant.SUCCESS);
        }

        List<String> destinationCodes = recentSearchList
            .stream()
            .map(RecentSearchEntity::getDestinationCode)
            .collect(Collectors.toList());

        return responseBodyWithSuccessMessage(availabilitySV.getAvailabilityByDesCodes(destinationCodes));
    }

}
