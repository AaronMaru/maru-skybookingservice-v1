package com.skybooking.eventservice.v1_0_0.service.email;

import com.skybooking.eventservice.v1_0_0.ui.model.request.email.*;
import com.skybooking.eventservice.v1_0_0.ui.model.response.S3UploadRS;
import com.skybooking.eventservice.v1_0_0.util.email.EmailBean;
import com.skybooking.eventservice.v1_0_0.util.pdf.PdfBean;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.skybooking.eventservice.constant.EmailKey.*;

@Service
public class SkyPointEmailIP implements SkyPointEmailSV {

    @Autowired
    private EmailBean emailBean;

    @Autowired
    private Configuration configuration;

    @Autowired
    private PdfBean pdfBean;

    @Override
    public void send(SendRQ sendRQ) {

        Map<String, Object> mailData = emailBean.mailData(sendRQ.getEmail(), sendRQ.getFullName(),
                sendRQ.getAmount(), SKY_POINT_TOP_UP);
        emailBean.email(mailData, null);

    }

    @Override
    public S3UploadRS topUpSuccess(SkyPointTopUpSuccessRQ skyPointTopUpSuccessRQ) {

        Map<String, Object> mailData = emailBean.mailData(skyPointTopUpSuccessRQ.getEmail(), skyPointTopUpSuccessRQ.getFullName(),
                skyPointTopUpSuccessRQ.getAmount(), SKY_POINT_TOP_UP);

        mailData.put("earnAmount", skyPointTopUpSuccessRQ.getEarnAmount());

        List<File> fileList = new ArrayList<>();

        Map<String, Object> pdfData = new HashMap<>();
        pdfData.put("data", skyPointTopUpSuccessRQ);
        pdfData.put("pdfTemplate", "top_up");
        pdfData.put("label_topUp", emailBean.dataPdfTemplate("api_receipt_top_up_pdf"));
        S3UploadRS s3UploadRS = new S3UploadRS();
        try {
            File file = pdfBean.pdfRender(configuration, pdfData);

            s3UploadRS = pdfBean.uploadPdfTos3bucket(file);

            fileList.add(file);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        emailBean.email(mailData, fileList);

        return s3UploadRS;
    }

    @Override
    public void topUpFailed(SkyPointTopUpFailedRQ skyPointTopUpFailedRQ) {
        Map<String, Object> mailData = emailBean.mailData(skyPointTopUpFailedRQ.getEmail(), skyPointTopUpFailedRQ.getFullName(),
                skyPointTopUpFailedRQ.getAmount(), SKY_POINT_TOP_UP_FAILED);

        emailBean.email(mailData, null);
    }

    @Override
    public void earning(SkyPointEarnedRQ skyPointEarnedRQ) {

        Map<String, Object> mailData = emailBean.mailData(skyPointEarnedRQ.getEmail(),
                skyPointEarnedRQ.getFullName(),
                skyPointEarnedRQ.getAmount(), SKY_POINT_EARNED);
        mailData.put("transactionId", skyPointEarnedRQ.getTransactionCode());
        emailBean.email(mailData, null);

    }

    @Override
    public void redeem(SkyPointRedeemRQ skyPointRedeemRQ) {

        Map<String, Object> mailData = emailBean.mailData(skyPointRedeemRQ.getEmail(), skyPointRedeemRQ.getFullName(),
                skyPointRedeemRQ.getAmount(), SKY_POINT_REDEEM);
        mailData.put("transactionId", skyPointRedeemRQ.getTransactionCode());

        emailBean.email(mailData, null);
    }

    @Override
    public void refund(SkyPointRefundRQ skyPointRefundRQ) {

        Map<String, Object> mailData = emailBean.mailData(skyPointRefundRQ.getEmail(), skyPointRefundRQ.getFullName(),
                skyPointRefundRQ.getAmount(), SKY_POINT_REFUND);

        emailBean.email(mailData, null);
    }
}
