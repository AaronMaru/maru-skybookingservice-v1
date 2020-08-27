package com.skybooking.stakeholderservice.v1_0_0.service.implement.notification;

import com.skybooking.stakeholderservice.constant.AwsPartConstant;
import com.skybooking.stakeholderservice.constant.BookingKeyConstant;
import com.skybooking.stakeholderservice.constant.NotificationConstant;
import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.notification.NotificationEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeholderUserInvitationEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.NotificationBookingTO;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.NotificationDetailTO;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.NotificationNQ;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.NotificationTO;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.notification.NotificationRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.StakeHolderUserRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.StakeholderUserInvitationRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.notification.NotificationSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.FilterRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification.NotificationBookingRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification.NotificationDetailRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification.NotificationPagingRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification.NotificationRS;
import com.skybooking.stakeholderservice.v1_0_0.util.JwtUtils;
import com.skybooking.stakeholderservice.v1_0_0.util.general.AwsPartBean;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class NotificationIP implements NotificationSV {

    @Autowired
    private NotificationRP notificationRP;

    @Autowired
    private NotificationNQ notificationNQ;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private StakeHolderUserRP skyuserRP;

    @Autowired
    private CompanyRP companyRP;

    @Autowired
    private StakeholderUserInvitationRP inviteRP;

    @Autowired
    private AwsPartBean awsPartBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Details notification
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param id
     */
    public NotificationDetailRS detailNotification(Long id) {

        NotificationDetailTO notificationDetailTO = notificationNQ.notificationDetail(headerBean.getLocalizationId(), id);

        if ( notificationDetailTO == null ) {
            throw new BadRequestException("sth_w_w", null);
        }

        NotificationDetailRS notificationDetailRS = new NotificationDetailRS();
        BeanUtils.copyProperties(notificationDetailTO, notificationDetailRS);

        NotificationTO notificationTo = new NotificationTO();
        BeanUtils.copyProperties(notificationDetailTO, notificationTo);

        var subject = notifySubject(notificationTo);
        String title = scriptReplace(notificationTo);
        notificationDetailRS.setTitle(title);
        notificationDetailRS.setSubject(subject.getId() != null ? subject.getId().toString() : "");
        notificationDetailRS.setReadable(notificationDetailTO.getReadable() == 1 ? true : false);

        notificationDetailRS.setPhoto(awsPartBean.partUrl(AwsPartConstant.SKYUSER_PROFILE_SMALL, notificationDetailTO.getPhoto()));
        notificationDetailRS.setNotiIcon(awsPartBean.partUrl(AwsPartConstant.SKYUSER_PROFILE_SMALL, notificationDetailTO.getPhoto()));

        return notificationDetailRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting list of notification
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param action
     */
    public NotificationPagingRS getNotifications() {

        FilterRQ filterRQ = new FilterRQ(httpServletRequest, jwtUtils.getUserToken());
        BookingKeyConstant bookingKeyConstant =  new BookingKeyConstant();

        Integer size = filterRQ.getSize();
        Integer page = filterRQ.getPage();

        String stake = headerBean.getCompanyId(filterRQ.getCompanyHeaderId());

        Page<NotificationTO> notificationTOS = notificationNQ.listNotification(
                stake,
                headerBean.getLocalizationId(),
                filterRQ.getCompanyHeaderId(),
                filterRQ.getSkyuserId(),
                filterRQ.getRole(),
                PageRequest.of(page, size) );

        List<NotificationRS> notificationRS = new ArrayList<>();
        for (NotificationTO notification : notificationTOS) {
            NotificationRS notificationRS1 = new NotificationRS();
            BeanUtils.copyProperties(notification, notificationRS1);

            notificationRS1.setPhoto(awsPartBean.partUrl(AwsPartConstant.SKYUSER_PROFILE_SMALL, notification.getPhoto()));
            notificationRS1.setNotiIcon(awsPartBean.partUrl(AwsPartConstant.SKYUSER_PROFILE_SMALL, notification.getPhoto()));

            if (filterRQ.getReadAll()) {
                readable((long) notification.getId());
                notification.setReadable(1);
            }

            notificationRS1.setReadable(notification.getReadable() == 1 ? true : false);
            String title = scriptReplace(notification);
            var subject = notifySubject(notification);
            notificationRS1.setTitle(title);
            if (subject.getId() != null) {
                notificationRS1.setSubject(subject.getId().toString());
                notificationRS1.setSubjectStatus(subject.getStatus().toString());
            }

            if ( notification.getBookingId() != null && !notification.getBookingCode().equals("") ) {

                if (notification.getTripType().equals("OneWay")) {
                    notificationRS1.setTripType(bookingKeyConstant.ONEWAY);
                }

                if (notification.getTripType().equals("Return")) {
                    notificationRS1.setTripType(bookingKeyConstant.ROUND);
                }
                if (notification.getTripType().equals("Other")) {
                    notificationRS1.setTripType(bookingKeyConstant.MULTICITY);
                }

                List<NotificationBookingTO> notificationBookingTOS = notificationNQ.notificationFlightBooking( headerBean.getLocalizationId(), notification.getBookingId() );
                List<NotificationBookingRS> notificationBookingRS = new ArrayList<>();
                for (NotificationBookingTO notificationBooking : notificationBookingTOS) {
                    NotificationBookingRS notificationBookingRS1 = new NotificationBookingRS();
                    BeanUtils.copyProperties(notificationBooking, notificationBookingRS1);
                    notificationBookingRS.add(notificationBookingRS1);
                }

                notificationRS1.setBookingLegs(notificationBookingRS);

            }

            notificationRS.add(notificationRS1);

        }

        String unreadNotify = notificationRP.countAllBy(filterRQ.getSkyuserId(), filterRQ.getCompanyHeaderId() != 0 ? filterRQ.getCompanyHeaderId() : null);

        NotificationPagingRS notificationPagingRS = new NotificationPagingRS();
        notificationPagingRS.setSize(size);
        notificationPagingRS.setPage(page + 1);
        notificationPagingRS.setData(notificationRS);
        notificationPagingRS.setTotals(notificationTOS.getTotalElements());
        notificationPagingRS.setUnRead(Integer.parseInt(unreadNotify));

        return notificationPagingRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Remove notification
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param id
     */
    public void removeNF(Long id) {
        var notif = checkNotify(id);
        notificationRP.delete(notif);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Read notification
     * -----------------------------------------------------------------------------------------------------------------
     */
    public NotificationDetailRS readable(Long id) {

        var notif = checkNotify(id);
        if (notif.getReadable() == null || notif.getReadable() == 0) {
            notif.setReadable(1);
            notificationRP.save(notif);
            return detailNotification(id);
        }

        return null;

    }
    private NotificationEntity checkNotify(Long id) {
        FilterRQ filterRQ = new FilterRQ(httpServletRequest, jwtUtils.getUserToken());
        var notif = notificationRP.findByIdAndStakeholderUserId(id, filterRQ.getSkyuserId());

        if (filterRQ.getCompanyHeaderId() != 0) {
            notif = notificationRP.findByIdAndStakeholderCompanyId(id, filterRQ.getCompanyHeaderId());
        }
        if (notif == null) {
            throw new BadRequestException("sth_w_w", null);
        }

        return notif;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Script replace on notification
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param notificationTo
     * @Return String
     */
    private String scriptReplace(NotificationTO notificationTo) {

        HashMap<String, String> scriptData = new HashMap<>();

        var skyuser = skyuserRP.findById((long) notificationTo.getSkyuserId()).get();

        scriptData.put(NotificationConstant.FULL_NAME, skyuser.getLastName() + " " + skyuser.getFirstName());
        scriptData.put(NotificationConstant.DATE_TIME, notificationTo.getCreatedAt().toString());

        if (notificationTo.getCompanyId() != null) {
            var company = companyRP.findById((long) notificationTo.getCompanyId());
            if (!company.isEmpty()) {
                scriptData.put(NotificationConstant.COMPANY_NAME, company.get().getCompanyName());
            }
        }

        for (Map.Entry<String, String> data : NotificationConstant.STRING_REPLACE.entrySet()) {

            if (notificationTo.getTitle().contains(data.getValue())) {

                if (NotificationConstant.CURRENT_ROLE.equals(data.getKey())) {
                    scriptData.put(NotificationConstant.CURRENT_ROLE, notificationTo.getReplaceOne() != null ? notificationTo.getReplaceOne() : "old");
                }
                if (NotificationConstant.NEW_ROLE.equals(data.getKey())) {
                    scriptData.put(NotificationConstant.NEW_ROLE, notificationTo.getReplaceTwo() != null ? notificationTo.getReplaceTwo() : "new");
                }

                notificationTo.setTitle(notificationTo.getTitle().replace(data.getValue(), scriptData.get(data.getKey())));
            }

        }

        return notificationTo.getTitle();

    }


    private StakeholderUserInvitationEntity notifySubject(NotificationTO notificationTo) {
        if (NotificationConstant.ADD_SKYUSER.equals(notificationTo.getNotiType())) {
            if (notificationTo.getReplaceOne() != null) {
                Optional<StakeholderUserInvitationEntity> invite = inviteRP.findById((long) Integer.parseInt(notificationTo.getReplaceOne()));
                if (!invite.isEmpty()) {
                    return invite.get();
                }
            }
        }
        return new StakeholderUserInvitationEntity();
    }

}
