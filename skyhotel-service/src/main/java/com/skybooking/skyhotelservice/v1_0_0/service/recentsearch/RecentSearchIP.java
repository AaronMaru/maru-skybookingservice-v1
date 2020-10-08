package com.skybooking.skyhotelservice.v1_0_0.service.recentsearch;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.recentsearch.RecentSearchEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.recentsearch.RecentSearchRP;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.search.RecentSearchRS;
import com.skybooking.skyhotelservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhotelservice.v1_0_0.util.datetime.DatetimeUtil;
import com.skybooking.skyhotelservice.v1_0_0.util.header.HeaderCM;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecentSearchIP extends BaseServiceIP implements RecentSearchSV {

    @Autowired
    private RecentSearchRP recentSearchRP;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HeaderCM headerCM;

    @Autowired
    HttpServletRequest httpServletRequest;

    public StructureRS listing()
    {
        Long skyuserId = jwtUtils.userToken().getSkyuserId();
        Long companyId = headerCM.getCompanyIdZ();

        List<RecentSearchEntity> recentSearchEntities = recentSearchRP.listRecentSearch(skyuserId, companyId);
        List<RecentSearchRS> recentSearches = recentSearchEntities
            .stream()
            .map(RecentSearchRS::new)
            .collect(Collectors.toList());

        return responseBodyWithSuccessMessage(recentSearches);
    }

    public StructureRS delete()
    {
        Long skyuserId = jwtUtils.userToken().getSkyuserId();
        Long companyId = headerCM.getCompanyIdZ();

        recentSearchRP.clearRecentSearch(skyuserId, companyId);
        return responseBodyWithSuccessMessage();
    }

    public void saveOrUpdate(AvailabilityRQ availabilityRQ)
    {
        /* Check have access token */
        if (httpServletRequest.getHeader("Authorization") == null) return;

        Long skyuserId = jwtUtils.userToken().getSkyuserId();
        Long companyId = headerCM.getCompanyIdZ();

        /* Check if user not log in */
        if (skyuserId == null) return;

        String destinationCode = availabilityRQ.getDestination().getCode();

        RecentSearchEntity recentSearchEntity = recentSearchRP
                .existsRecentSearch(skyuserId, companyId, destinationCode)
                .orElse(new RecentSearchEntity());

        recentSearchEntity.setStakeholderUserId(skyuserId);
        recentSearchEntity.setStakeholderCompanyId(companyId);
        recentSearchEntity.setCheckIn(DatetimeUtil.toDate(availabilityRQ.getCheckIn()));
        recentSearchEntity.setCheckOut(DatetimeUtil.toDate(availabilityRQ.getCheckOut()));
        recentSearchEntity.setDestinationCode(destinationCode);
        recentSearchEntity.setRoom(availabilityRQ.getRoom());
        recentSearchEntity.setAdult(availabilityRQ.getAdult());
        recentSearchEntity.setChildren(availabilityRQ.getChildren().size());
        recentSearchEntity.setSearchedCount(recentSearchEntity.getSearchedCount() + 1);
        recentSearchEntity.setGroupDestination(availabilityRQ.getDestination().getGroup());
        recentSearchEntity.setCountry(null);//Keep

        recentSearchRP.save(recentSearchEntity);
    }

}
