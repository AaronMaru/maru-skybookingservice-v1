package com.skybooking.stakeholderservice.v1_0_0.util.notification;

import com.skybooking.stakeholderservice.constant.NotificationConstant;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.CompanyByRolePlayerTO;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.NotificationNQ;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification.StakeholderUserPlayerTO;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification.CompanyByRolePlayerRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification.StakeholderUserPlayerRS;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class NotificationBean {

    @Autowired
    private NotificationNQ notificationNQ;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Stakeholder user player id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return List<StakeholderUserPlayerRS>
     */
    public List<StakeholderUserPlayerRS> userPlayerId(Long skyuserId) {

        List<StakeholderUserPlayerTO> playersTO = notificationNQ.stakeholderPlayerId(skyuserId);
        List<StakeholderUserPlayerRS> playersRS = new ArrayList<>();

        for (StakeholderUserPlayerTO playerTO : playersTO) {
            StakeholderUserPlayerRS playerRS = new StakeholderUserPlayerRS();
            BeanUtils.copyProperties(playerTO, playerRS);
            playersRS.add(playerRS);
        }

        return playersRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Company player id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return List<CompanyPlayerRS>
     */
//    public List<CompanyPlayerRS> companyPlayerId(Long companyId, Long skyuserId) {
//
//        List<CompanyPlayerTO> playersTO = notificationNQ.companyPlayerId(companyId, skyuserId);
//        List<CompanyPlayerRS> playersRS = new ArrayList<>();
//
//        for (CompanyPlayerTO playerTO : playersTO) {
//            CompanyPlayerRS playerRS = new CompanyPlayerRS();
//            BeanUtils.copyProperties(playerTO, playerRS);
//            playersRS.add(playerRS);
//        }
//
//        return playersRS;
//
//    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Company player id by role
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return List<CompanyByRolePlayerRS>
     */
    public List<CompanyByRolePlayerRS> companyByRolePlayerId(String role, Long companyId, Long skyuserId) {

        List<CompanyByRolePlayerTO> playersTO = notificationNQ.companyByRolePlayerId(role, companyId, skyuserId);
        List<CompanyByRolePlayerRS> playersRS = new ArrayList<>();

        for (CompanyByRolePlayerTO playerTO : playersTO) {
            CompanyByRolePlayerRS playerRS = new CompanyByRolePlayerRS();
            BeanUtils.copyProperties(playerTO, playerRS);
            playersRS.add(playerRS);
        }

        return playersRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Set string replace
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return String
     */
    public String scriptStringReplace(String subject, HashMap<String, Object> dataScript) {

        dataScript.put(NotificationConstant.FULL_NAME, dataScript.get("fullname"));
        dataScript.put(NotificationConstant.DATE_TIME, new Date().toString());

        for (Map.Entry<String, String> data : NotificationConstant.STRING_REPLACE.entrySet()) {
            if (subject.contains(data.getValue())) {
                subject = subject.replace(data.getValue(), dataScript.get(data.getKey()).toString());
            }
        }

        return subject;

    }


}
