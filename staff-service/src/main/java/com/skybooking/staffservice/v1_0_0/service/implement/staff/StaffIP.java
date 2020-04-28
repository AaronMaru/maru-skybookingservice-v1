package com.skybooking.staffservice.v1_0_0.service.implement.staff;

import com.skybooking.staffservice.constant.BookingStatusConstant;
import com.skybooking.staffservice.constant.StaffStatusConstant;
import com.skybooking.staffservice.exception.httpstatus.BadRequestException;
import com.skybooking.staffservice.exception.httpstatus.ForbiddenException;
import com.skybooking.staffservice.exception.httpstatus.NotFoundException;
import com.skybooking.staffservice.v1_0_0.io.enitity.company.StakeholderUserHasCompanyEntity;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff.*;
import com.skybooking.staffservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import com.skybooking.staffservice.v1_0_0.service.interfaces.staff.StaffSV;
import com.skybooking.staffservice.v1_0_0.ui.model.request.FilterRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.request.staff.DeactiveStaffRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.response.staff.*;
import com.skybooking.staffservice.v1_0_0.util.JwtUtils;
import com.skybooking.staffservice.v1_0_0.util.datetime.DateTimeBean;
import com.skybooking.staffservice.v1_0_0.util.notification.PushNotificationOptions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class StaffIP implements StaffSV {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CompanyHasUserRP companyHasUserRP;

    @Autowired
    private StaffNQ staffNQ;

    @Autowired
    private Environment environment;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private DateTimeBean dateTimeBean;

    @Autowired
    private PushNotificationOptions notification;

    public StaffPaginationRS getStaff(HttpServletRequest request) {

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());
        filterRQ.setAction( checkSearchOrFilter() );

        Page<StaffTO> staffsTO = staffNQ.listStaff(
                filterRQ.getCompanyId(),
                filterRQ.getKeyword(),
                filterRQ.getRole(),
                filterRQ.getStartPRange(),
                filterRQ.getEndPRange(),
                filterRQ.getJoinDate(),
                filterRQ.getJoinStatus(),
                filterRQ.getAction(),
                filterRQ.getActive(),
                filterRQ.getInactive(),
                filterRQ.getBanned(),
                environment.getProperty("spring.awsImageUrl.profile.url_larg"),
                filterRQ.getSortField(),
                filterRQ.getOrderBy(),
                PageRequest.of(filterRQ.getPage(), filterRQ.getSize()));

        List<StaffRS> staffs = staffs(staffsTO);

        StaffPaginationRS staffPaging = new StaffPaginationRS();

        staffPaging.setPage(filterRQ.getPage());
        staffPaging.setSize(filterRQ.getSize());
        staffPaging.setData(staffs);
        staffPaging.setTotals(staffsTO.getTotalElements());

        return staffPaging;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Determine is search or filter
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return String
     * @Param booking
     */
    public String checkSearchOrFilter() {

        if (request.getParameter("role") != null || request.getParameter("startPRange") != null ||
                request.getParameter("endPRange") != null || request.getParameter("joinDate") != null ||
                request.getParameter("joinStatus") != null
        ) {

            return "FILTER";

        } else if(request.getParameter("keyword") != null && !request.getParameter("keyword").equals("")) {

            return "SEARCH";

        }

        return "OTHER";

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Staffs
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param inviteStaffNoAccRQ
     */
    public List<StaffRS> staffs(Page<StaffTO> staffsTO) {

        Long companyId = jwtUtils.getClaim("companyId", Long.class);

        List<StaffRS> staffsRS = new ArrayList<>();

        BookingStatusConstant constant = new BookingStatusConstant();

        for (StaffTO staffTO : staffsTO) {
            StaffRS staffRS = new StaffRS();

            List<StaffTotalBookingTO> bookingListTO = staffNQ.listStaffTotalBooking(
                    staffTO.getSkyuserId(),
                    companyId,
                    constant.COMPLETED,
                    constant.UPCOMING,
                    constant.CANCELLED,
                    constant.FAILED
                    );

            BeanUtils.copyProperties(staffTO, staffRS);

            StaffTotalBookingRS bookingStatus = new StaffTotalBookingRS();
            AmountRS defaults = new AmountRS();

            bookingStatus.setCompleted(defaults);
            bookingStatus.setCancellation(defaults);
            bookingStatus.setUpcoming(defaults);
            bookingStatus.setFailed(defaults);

            for (StaffTotalBookingTO item : bookingListTO) {
                switch (item.getStatusKey()) {
                    case "COMPLETED" :
                        AmountRS complete = setAmount(item);
                        complete.setCurrencyCode(item.getCurrencyCode());
                        bookingStatus.setCompleted(complete);
                        break;
                    case "CANCELLED" :
                        AmountRS cancel = setAmount(item);
                        cancel.setCurrencyCode(item.getCurrencyCode());
                        bookingStatus.setCancellation(cancel);
                        break;
                    case "UPCOMING" :
                        AmountRS upcoming = setAmount(item);
                        upcoming.setCurrencyCode(item.getCurrencyCode());
                        bookingStatus.setUpcoming(upcoming);
                        break;
                    case "FAILED" :
                        AmountRS failed = setAmount(item);
                        failed.setCurrencyCode(item.getCurrencyCode());
                        bookingStatus.setFailed(failed);
                        break;
                }

            }
            staffRS.setFlightBooking(bookingStatus);

            staffsRS.add(staffRS);

        }

        return staffsRS;

    }

    public AmountRS setAmount(StaffTotalBookingTO item) {
        AmountRS amount = new AmountRS();
        amount.setAmount(item.getAmount());
        amount.setQuantity(item.getQuantity());

        return amount;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Staff profile
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param id
     * @Return Object
     */
    public Object staffProfile(Long id) {

        Long companyId = jwtUtils.getClaim("companyId", Long.class);

        StaffStatusConstant staffConstant = new StaffStatusConstant();

        List<StaffProfileTO> staffTO = staffNQ.findProfileStaff(
                                    id,
                                    companyId,
                                    staffConstant.ACTIVE,
                                    staffConstant.INACTIVE,
                                    staffConstant.BANNED
                            );
        StaffProfileRS staffRS = new StaffProfileRS();

        if (staffTO.stream().findFirst().isEmpty()) {
            throw new NotFoundException("This url not found", null);
        }

        BeanUtils.copyProperties(staffTO.stream().findFirst().get(), staffRS);

        SkyuserTO skyUser = staffNQ.findSkyuser(id, environment.getProperty("spring.awsImageUrl.profile.url_larg"));
        BeanUtils.copyProperties(skyUser, staffRS);

        staffRS.setJoinDate(dateTimeBean.convertDateTime(skyUser.getCreatedAt()));

        return staffRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Deactive staff
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param staffIdRQ
     */
    public void deactiveStaff(DeactiveStaffRQ deactiveStaffRQ) {

        FilterRQ filterRQ = new FilterRQ(request, jwtUtils.getUserToken());
        StakeholderUserHasCompanyEntity staff = companyHasUserRP.findByStakeholderCompanyIdAndStakeholderUserId(filterRQ.getCompanyHeaderId(), deactiveStaffRQ.getSkyuserId());
        if ( !filterRQ.getUserRole().equals("admin") ) {
            throw new ForbiddenException("Can not access this api", null);
        }
        if ( staff == null ) {
            throw new BadRequestException("sth_w_w", null);
        }
        if ( staff.getStatus() == 1 ) {
            throw new BadRequestException("Can not update skyowner role", null);
        }
        if ( filterRQ.getSkyuserId().equals(deactiveStaffRQ.getSkyuserId()) ) {
            throw new BadRequestException("Can not update yourselft", null);
        }
        if ( deactiveStaffRQ.getStatus() != null && !deactiveStaffRQ.getStatus().equals("") ) {
            staff.setJoinStatus(deactiveStaffRQ.getStatus());
            String keyNotification = (deactiveStaffRQ.getStatus() == 1) ? "skyowner_enabled_staff" : "skyowner_disabled_staff";
            notification.sendMessageToUsers(keyNotification, null, null);
        }
        if ( deactiveStaffRQ.getRole() != null && !deactiveStaffRQ.getRole().equals("") ) {
            staff.setSkyuserRole(deactiveStaffRQ.getRole());
        }
        companyHasUserRP.save(staff);

    }

}
