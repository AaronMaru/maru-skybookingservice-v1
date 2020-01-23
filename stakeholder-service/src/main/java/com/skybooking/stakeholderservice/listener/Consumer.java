package com.skybooking.stakeholderservice.listener;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.setting.SettingSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.setting.SendDownloadLinkRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.skybooking.stakeholderservice.config.ActiveMQConfig.ORDER_QUEUE;

/**
 * Created by : maru
 * Date  : 1/22/2020
 * Time  : 4:47 PM
 */

@Component
public class Consumer {

    @Autowired
    private SettingSV settingSV;

    @JmsListener(destination = ORDER_QUEUE)
    public void consume(@Payload SendDownloadLinkRQ sendDownloadLinkRQ) {

        System.out.println(sendDownloadLinkRQ.toString());
        settingSV.sendLinkDownload(sendDownloadLinkRQ);
    }
}
