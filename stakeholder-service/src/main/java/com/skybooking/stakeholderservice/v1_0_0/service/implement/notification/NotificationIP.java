package com.skybooking.stakeholderservice.v1_0_0.service.implement.notification;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.notification.NotificationEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.NotificationNQ;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.NotificationTO;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.notification.NotificationSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification.NotificationDetailRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification.NotificationRS;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationIP implements NotificationSV {

    @Autowired
    private UserBean userBean;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationNQ notificationNQ;

    @Autowired
    private HeaderBean headerBean;



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Details notification
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param id
     */
    public NotificationDetailRS detailNotification(Long id) {

        UserEntity userEntity = userBean.getUserPrincipal();

        var checkExist = userEntity.getStakeHolderUser().getNotification().stream().filter(c -> c.getId() == id).findFirst();
        if (checkExist.isEmpty()) {
            throw new BadRequestException("sth_w_w", "");
        }

        List<NotificationTO> notiTO = notificationNQ.notificationDetail(headerBean.getLocalizationId(), id);
        NotificationDetailRS notiRS = new NotificationDetailRS();

        if (notiTO.size() == 0) {
            throw new BadRequestException("sth_w_w", "");
        }
        BeanUtils.copyProperties(notiTO.stream().findFirst().get(), notiRS);

        return notiRS;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting list of notification
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param action
     */
    public List<NotificationRS> getNotifications(String action) {

        UserEntity userEntity = userBean.getUserPrincipal();
        Long companyId = null;

        if (userEntity.getStakeHolderUser().getIsSkyowner() == 1) {
            companyId  = userEntity.getStakeHolderUser().getStakeholderCompanies().stream().findFirst().get().getId();
        }

        List<NotificationTO> notiesTO = notificationNQ.listNotification(action, headerBean.getLocalizationId(), companyId, userEntity.getStakeHolderUser().getId());
        List<NotificationRS> notiesRS = new ArrayList<>();

        for (NotificationTO notiTO : notiesTO) {

            NotificationRS notiRS = new NotificationRS();
            BeanUtils.copyProperties(notiTO, notiRS);
            notiesRS.add(notiRS);

        }

        return notiesRS;

    }



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Remove notification
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param id
     */
    public void removeNF(Long id) {

        UserEntity userEntity = userBean.getUserPrincipal();

        List<NotificationEntity> notifications = userEntity.getStakeHolderUser().getNotification();

        Optional<NotificationEntity> notification = notifications.stream().filter(c -> c.getId() == id).findFirst();

        if(notification.isEmpty()) {
            throw new BadRequestException("sth_w_w", "");
        }

        userEntity.getStakeHolderUser().getNotification().remove(notification.get());
        userRepository.save(userEntity);

    }


}
