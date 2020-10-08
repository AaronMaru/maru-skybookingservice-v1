package com.skybooking.eventservice.v1_0_0.ui.controller.skypoint;

import com.skybooking.eventservice.v1_0_0.service.download.SkyPointDownloadSV;
import com.skybooking.eventservice.v1_0_0.ui.model.request.email.SkyPointTopUpRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.eventservice.v1_0_0.ui.model.response.S3UploadRS;
import com.skybooking.eventservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0/sky-point/download")
public class SkyPointDownloadController {

    @Autowired
    private SkyPointDownloadSV skyPointDownloadSV;

    @Autowired
    private LocalizationBean localization;

    @PostMapping("receipt")
    public ResRS topUpSkyPoint(@Valid @RequestBody SkyPointTopUpRQ skyPointTopUpRQ) {

        S3UploadRS s3UploadRS = skyPointDownloadSV.downloadReceipt(skyPointTopUpRQ);

        return localization.resAPI(HttpStatus.OK, "is_skb_info", s3UploadRS);
    }

}
