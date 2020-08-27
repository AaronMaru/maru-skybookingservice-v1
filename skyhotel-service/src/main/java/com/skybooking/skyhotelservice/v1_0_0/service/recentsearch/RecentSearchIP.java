package com.skybooking.skyhotelservice.v1_0_0.service.recentsearch;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.recentsearch.RecentSearchEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.recentsearch.RecentSearchRP;
import com.skybooking.skyhotelservice.v1_0_0.service.BaseServiceIP;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel.AvailabilityRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.search.RecentSearchRS;
import com.skybooking.skyhotelservice.v1_0_0.util.JwtUtils;
import com.skybooking.skyhotelservice.v1_0_0.util.datetime.DatetimeUtil;
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
    HttpServletRequest httpServletRequest;

    public StructureRS listing()
    {
        int userId = jwtUtils.getClaim("stakeholderId", Integer.class);
        Integer companyId = (Integer) ConvertUtils.convert(httpServletRequest.getHeader("X-CompanyId"), Integer.class);

        List<RecentSearchEntity> recentSearchEntities = recentSearchRP.listRecentSearch(userId, companyId);
        List<RecentSearchRS> recentSearches = recentSearchEntities
            .stream()
            .map(RecentSearchRS::new)
            .collect(Collectors.toList());

        return responseBodyWithSuccessMessage(recentSearches);
    }

    public StructureRS delete()
    {
        int userId = jwtUtils.getClaim("stakeholderId", Integer.class);
        Integer companyId = (Integer) ConvertUtils.convert(httpServletRequest.getHeader("X-CompanyId"), Integer.class);
        recentSearchRP.clearRecentSearch(userId, companyId);
        return responseBodyWithSuccessMessage();
    }

    public void saveOrUpdate(AvailabilityRQ availabilityRQ)
    {
        /* check have access token */
        if (httpServletRequest.getHeader("Authorization") == null) return;

        Integer userId = jwtUtils.getClaim("stakeholderId", Integer.class);
        Integer companyId = (Integer) ConvertUtils.convert(httpServletRequest.getHeader("X-CompanyId"), Integer.class);

        /* check if user not log in */
        if (userId == null) return;

        String destinationCode = availabilityRQ.getDestination().getCode();
        RecentSearchEntity recentSearchEntity = recentSearchRP.existsRecentSearch(destinationCode, userId, companyId);

        if (recentSearchEntity == null) {
            recentSearchEntity = new RecentSearchEntity();
            recentSearchEntity.setSearchedCount(0);
        }

        recentSearchEntity.setStakeholderUserId(userId);
        recentSearchEntity.setStakeholderCompanyId(companyId);
        recentSearchEntity.setCheckIn(DatetimeUtil.toDate(availabilityRQ.getCheckIn()));
        recentSearchEntity.setCheckOut(DatetimeUtil.toDate(availabilityRQ.getCheckOut()));
        recentSearchEntity.setDestinationCode(destinationCode);
        recentSearchEntity.setRoom(availabilityRQ.getRoom());
        recentSearchEntity.setAdult(availabilityRQ.getAdult());
        recentSearchEntity.setChildren(availabilityRQ.getChildren().size());
        recentSearchEntity.setSearchedCount(recentSearchEntity.getSearchedCount() + 1);

        recentSearchRP.save(recentSearchEntity);
    }

}
