package com.skybooking.eventservice.v1_0_0.service.download;

import com.skybooking.eventservice.v1_0_0.ui.model.request.email.SkyPointTopUpRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.response.S3UploadRS;

public interface SkyPointDownloadSV {

    S3UploadRS downloadReceipt(SkyPointTopUpRQ skyPointTopUpRQ);

}
