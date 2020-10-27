package com.skybooking.eventservice.v1_0_0.service.notification;

import com.skybooking.eventservice.v1_0_0.ui.model.request.notification.NotificationRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.request.notification.NotificationTopUpRQ;
import com.skybooking.eventservice.v1_0_0.util.auth.JwtUtils;
import com.skybooking.eventservice.v1_0_0.util.notification.NotificationDTO;
import com.skybooking.eventservice.v1_0_0.util.notification.PushNotificationOptions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class NotificationIP implements NotificationSV {

    @Autowired
    private PushNotificationOptions pushNotification;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public void topUp(NotificationTopUpRQ notificationTopUpRQ) {

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notificationTopUpRQ, notificationDTO);

        pushNotification.sendMessageToUsers(notificationDTO);

    }


    @Override
    public void sendNotification(NotificationRQ notificationRQ) {
        Long companyId = httpServletRequest.getHeader("X-CompanyId") != null &&
                !httpServletRequest.getHeader("X-CompanyId").isEmpty() ?
                Long.valueOf(httpServletRequest.getHeader("X-CompanyId")) : null;

        Long stakeholderUserId = jwtUtils.getClaim("stakeholderId", Long.class);


        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notificationRQ, notificationDTO);
        String notificationType = notificationRQ.getType();
        if (notificationType.equalsIgnoreCase("EARNED_POINT")) {
            if (notificationRQ.getTransactionFor().equalsIgnoreCase("FLIGHT")) {
                notificationDTO.setScript("skp_earn_flight_notification");
            } else {
                notificationDTO.setScript("skp_earn_hotel_notification");
            }
        } else if (notificationType.equalsIgnoreCase("REDEEM_POINT")) {
            if (notificationRQ.getTransactionFor().equalsIgnoreCase("FLIGHT")) {
                notificationDTO.setScript("skp_redeem_flight_notification");
            } else {
                notificationDTO.setScript("skp_redeem_hotel_notification");
            }
        } else if (notificationType.equalsIgnoreCase("REFUND_POINT")) {
            notificationDTO.setScript("skp_refund_notification");
        }
        notificationDTO.setStakeholderUserId(stakeholderUserId);
        notificationDTO.setStakeholderCompanyId(companyId);

        pushNotification.sendMessageToUsers(notificationDTO);

    }


}
