package com.skybooking.skypointservice.v1_0_0.service.upgradeLevel;

import com.skybooking.skypointservice.v1_0_0.client.event.action.EventNotificationAction;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.NotificationUpgradeLevelRQ;
import com.skybooking.skypointservice.v1_0_0.client.event.model.requset.SkyPointUpgradeLevelRQ;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.UserReferenceRS;
import com.skybooking.skypointservice.v1_0_0.helper.SendMailSMSHelper;
import com.skybooking.skypointservice.v1_0_0.io.entity.config.ConfigUpgradeLevelEntity;
import com.skybooking.skypointservice.v1_0_0.io.entity.upgradeLevel.UpgradeLevelHistoryEntity;
import com.skybooking.skypointservice.v1_0_0.io.repository.config.ConfigUpgradeLevelRP;
import com.skybooking.skypointservice.v1_0_0.io.repository.upgradeLevel.UpgradeLevelHistoryRP;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UpgradeLevelIP implements UpgradeLevelSV {

    @Autowired
    private UpgradeLevelHistoryRP upgradeLevelHistoryRP;

    @Autowired
    private EventNotificationAction eventNotificationAction;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private SendMailSMSHelper sendMailSMSHelper;

    @Autowired
    private ConfigUpgradeLevelRP configUpgradeLevelRP;

    @Override
    public void save(UserReferenceRS userReferenceRS, Integer accountId, ConfigUpgradeLevelEntity configUpgradeLevelEntity) {

        UpgradeLevelHistoryEntity oldLevelHistory = upgradeLevelHistoryRP.findLastUpgrade(accountId);

        if (oldLevelHistory == null) {
            UpgradeLevelHistoryEntity upgradeLevelHistory = new UpgradeLevelHistoryEntity();
            upgradeLevelHistory.setAccountId(accountId);
            BeanUtils.copyProperties(configUpgradeLevelEntity, upgradeLevelHistory);
            upgradeLevelHistory.setLevelCode(configUpgradeLevelEntity.getCode());
            upgradeLevelHistory.setUpgradeLevelId(upgradeLevelHistory.getId());

            upgradeLevelHistoryRP.save(upgradeLevelHistory);

        } else if (!oldLevelHistory.getLevelCode().equals(configUpgradeLevelEntity.getCode())) {

            UpgradeLevelHistoryEntity upgradeLevelHistory = new UpgradeLevelHistoryEntity();
            upgradeLevelHistory.setAccountId(accountId);
            BeanUtils.copyProperties(configUpgradeLevelEntity, upgradeLevelHistory);
            upgradeLevelHistory.setUpgradeLevelId(configUpgradeLevelEntity.getId());
            upgradeLevelHistory.setLevelCode(configUpgradeLevelEntity.getCode());
            upgradeLevelHistoryRP.save(upgradeLevelHistory);


            ConfigUpgradeLevelEntity oldConfigLevel = configUpgradeLevelRP.findById(oldLevelHistory.getUpgradeLevelId()).orElse(null);
            ConfigUpgradeLevelEntity newConfigLevel = configUpgradeLevelRP.findById(upgradeLevelHistory.getUpgradeLevelId()).orElse(null);
            SkyPointUpgradeLevelRQ skyPointUpgradeLevelRQ = new SkyPointUpgradeLevelRQ();
            if (oldConfigLevel != null) {
                skyPointUpgradeLevelRQ.setOldLevel(oldConfigLevel.getLevelName());
            }
            if (newConfigLevel != null) {
                skyPointUpgradeLevelRQ.setNewLevel(newConfigLevel.getLevelName());
            }

            sendMailSMSHelper.sendMailOrSmsUpgradeLevel(userReferenceRS, httpServletRequest, skyPointUpgradeLevelRQ);

            NotificationUpgradeLevelRQ notificationUpgradeLevelRQ = new NotificationUpgradeLevelRQ();
            notificationUpgradeLevelRQ.setLevel("Silver");
            notificationUpgradeLevelRQ.setType("UPGRADE_LEVEL");

            eventNotificationAction.sendNotificationUpgradeLevel(httpServletRequest, notificationUpgradeLevelRQ);

        }
    }
}
