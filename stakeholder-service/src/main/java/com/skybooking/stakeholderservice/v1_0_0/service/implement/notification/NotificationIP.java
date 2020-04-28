package com.skybooking.stakeholderservice.v1_0_0.service.implement.notification;

import com.skybooking.stakeholderservice.constant.BookingKeyConstant;
import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.NotificationBookingTO;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.NotificationDetailTO;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.NotificationNQ;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.NotificationTO;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.notification.NotificationRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.notification.NotificationSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.FilterRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification.NotificationBookingRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification.NotificationDetailRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification.NotificationPagingRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification.NotificationRS;
import com.skybooking.stakeholderservice.v1_0_0.util.JwtUtils;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
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
public class NotificationIP implements NotificationSV {


    @Autowired
    private Environment environment;

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

        String imageName = notificationDetailTO.getPhoto() != null ? notificationDetailTO.getPhoto() : "default.png";
        notificationDetailRS.setPhoto(environment.getProperty("spring.awsImageUrl.profile.url_small")  + imageName);
        notificationDetailRS.setNotiIcon(environment.getProperty("spring.awsImageUrl.profile.url_small") + imageName);

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
        String stake = headerBean.getCompanyId(filterRQ.getCompanyHeaderId());
        BookingKeyConstant bookingKeyConstant =  new BookingKeyConstant();

        Integer size = filterRQ.getSize();
        Integer page = filterRQ.getPage();

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
            String imageName = notification.getPhoto() != null ? notification.getPhoto() : "default.png";
            notificationRS1.setPhoto(environment.getProperty("spring.awsImageUrl.profile.url_small")  + imageName);
            notificationRS1.setNotiIcon(environment.getProperty("spring.awsImageUrl.profile.url_small") + imageName);


            if ( notification.getBookingId() != null ) {

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

        NotificationPagingRS notificationPagingRS = new NotificationPagingRS();
        notificationPagingRS.setSize(size);
        notificationPagingRS.setPage(page + 1);
        notificationPagingRS.setData(notificationRS);
        notificationPagingRS.setTotals(notificationTOS.getTotalElements());

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

        FilterRQ filterRQ = new FilterRQ(httpServletRequest, jwtUtils.getUserToken());
        var notif = notificationRP.findNotification(id, filterRQ.getSkyuserId(), filterRQ.getCompanyHeaderId());

        if (notif == null) {
            throw new BadRequestException("sth_w_w", null);
        }

        notificationRP.delete(notif);

    }


}
