package com.skybooking.staffservice.v1_0_0.service.implement.staff;

import com.skybooking.staffservice.exception.httpstatus.BadRequestException;
import com.skybooking.staffservice.exception.httpstatus.NotFoundException;
import com.skybooking.staffservice.v1_0_0.io.enitity.company.StakeholderUserHasCompanyEntity;
import com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff.*;
import com.skybooking.staffservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import com.skybooking.staffservice.v1_0_0.service.interfaces.staff.StaffSV;
import com.skybooking.staffservice.v1_0_0.ui.model.request.invitation.DeactiveStaffRQ;
import com.skybooking.staffservice.v1_0_0.ui.model.response.staff.*;
import com.skybooking.staffservice.v1_0_0.util.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StaffIP implements StaffSV {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CompanyHasUserRP companyHasUserRP;

    @Autowired
    private StaffSvNQ staffNQ;

    @Autowired
    private Environment environment;

    @Autowired
    private JwtUtils jwtUtils;

    public StaffPaginationRS getStaff() {

        Long companyId = jwtUtils.getClaim("companyId", Long.class);

        String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";
        String role = request.getParameter("role") != null ? request.getParameter("role") : "";
        Integer startPRange = request.getParameter("startPRange") != null
                ? Integer.valueOf(request.getParameter("startPRange"))
                : 0;
        Integer endPRange = request.getParameter("endPRange") != null
                ? Integer.valueOf(request.getParameter("endPRange"))
                : 0;
        String joinDate = request.getParameter("joinDate") != null ? request.getParameter("joinDate") : "";
        String joinStatus = request.getParameter("joinStatus") != null ? request.getParameter("joinStatus") : "";

        Integer size = (request.getParameter("size") != null && !request.getParameter("size").isEmpty())
                ? Integer.valueOf(request.getParameter("size"))
                : 10;
        Integer page = (request.getParameter("page") != null && !request.getParameter("page").isEmpty())
                ? Integer.valueOf(request.getParameter("page")) - 1
                : 0;

        String action = checkSearchOrFilter(keyword, role, startPRange, endPRange, joinDate, joinStatus);

        Page<StaffTO> staffsTO = staffNQ.listStaff(companyId, keyword, role, startPRange, endPRange, joinDate,
                joinStatus, action, PageRequest.of(page, size));

        List<StaffRS> staffs = staffs(staffsTO);

        StaffPaginationRS staffPaging = new StaffPaginationRS();

        staffPaging.setPage(page);
        staffPaging.setSize(size);
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
    public String checkSearchOrFilter(String keyword, String role, Integer startPRange, Integer endPRange,
            String joinDate, String status) {

        String action = "search";
        if (request.getParameter("keyword") == null) {
            action = "filter";
        }
        if (request.getQueryString() == null) {
            action = "other";
        }
        if (keyword.equals("") && role.equals("") && startPRange.equals(0) && endPRange.equals(0) && joinDate.equals("")
                && status.equals("")) {
            action = "other";
        }

        return action;
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

        for (StaffTO staffTO : staffsTO) {
            StaffRS staffRS = new StaffRS();

            List<StaffTotalBookingTO> bookingListTO = staffNQ.listStaffTotalBooking(staffTO.getSkyuserId(), companyId);
            List<StaffTotalBookingTO> initialList = Arrays.asList(
                    new StaffTotalBookingTO(BigInteger.valueOf(0), BigDecimal.valueOf(0.0), "Upcoming"),
                    new StaffTotalBookingTO(BigInteger.valueOf(0), BigDecimal.valueOf(0.0), "Completed"),
                    new StaffTotalBookingTO(BigInteger.valueOf(0), BigDecimal.valueOf(0.0), "Cancelled"));

            bookingListTO.forEach(itemBooking -> {
                initialList.forEach(itemInit -> {
                    if (itemBooking.getStatusKey().equals(itemInit.getStatusKey())) {
                        itemInit.setStatus(itemBooking.getStatus());
                        itemInit.setAmount(itemBooking.getAmount());
                        itemInit.setQuantity(itemBooking.getQuantity());
                    }
                });
            });

            StaffTotalBookingRS bookingListRS = new StaffTotalBookingRS();

            initialList.forEach(itemInit -> {
                Upcoming upcoming = new Upcoming();
                Completed completed = new Completed();
                Cancellation cancellation = new Cancellation();

                if (itemInit.getStatusKey().equals("Upcoming")) {
                    upcoming.setAmount(itemInit.getAmount());
                    upcoming.setQuantity(itemInit.getQuantity());
                    bookingListRS.setUpcoming(upcoming);
                } else if (itemInit.getStatusKey().equals("Completed")) {
                    completed.setAmount(itemInit.getAmount());
                    completed.setQuantity(itemInit.getQuantity());
                    bookingListRS.setCompleted(completed);
                } else if (itemInit.getStatusKey().equals("Cancelled")) {
                    cancellation.setAmount(itemInit.getAmount());
                    cancellation.setQuantity(itemInit.getQuantity());
                    bookingListRS.setCancellation(cancellation);
                }
            });

            BeanUtils.copyProperties(staffTO, staffRS);
            staffRS.setFlightBooking(bookingListRS);

            staffRS.setPhotoMedium(environment.getProperty("spring.awsImageUrl.profile.url_larg") + staffTO.getPhoto());
            staffRS.setPhotoSmall(environment.getProperty("spring.awsImageUrl.profile.url_larg") + "_thumbnail/"
                    + staffTO.getPhoto());
            staffsRS.add(staffRS);

        }

        return staffsRS;

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

        List<StaffProfileTO> staffTO = staffNQ.findProfileStaff(id, companyId);
        StaffProfileRS staffRS = new StaffProfileRS();

        if (staffTO.stream().findFirst().isEmpty()) {
            throw new NotFoundException("This url not found", "");
        }

        BeanUtils.copyProperties(staffTO.stream().findFirst().get(), staffRS);

        if (staffTO.stream().findFirst().get().getAddedBy() != null) {
            SkyuserTO addedBy = staffNQ.findSkyuser((long) staffTO.stream().findFirst().get().getAddedBy());
            staffRS.setAddedBy(addedBy.getFirstName() + " " + addedBy.getLastName());
        }

        SkyuserTO skyUser = staffNQ.findSkyuser(id);
        BeanUtils.copyProperties(skyUser, staffRS);

        staffRS.setPhotoMedium(environment.getProperty("spring.awsImageUrl.profile.url_larg") + skyUser.getPhoto());
        staffRS.setPhotoSmall(
                environment.getProperty("spring.awsImageUrl.profile.url_larg") + "_thumbnail/" + skyUser.getPhoto());

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

        Long companyId = jwtUtils.getClaim("companyId", Long.class);

        StakeholderUserHasCompanyEntity staff = companyHasUserRP
                .findByStakeholderCompanyIdAndStakeholderUserId(companyId, deactiveStaffRQ.getSkyuserId());

        if (staff == null) {
            throw new BadRequestException("sth_w_w", "");
        }

        staff.setJoinStatus(0);
        companyHasUserRP.save(staff);

    }

}
