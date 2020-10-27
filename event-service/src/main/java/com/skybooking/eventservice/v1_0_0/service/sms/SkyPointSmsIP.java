package com.skybooking.eventservice.v1_0_0.service.sms;

import com.skybooking.eventservice.constant.TransactionForConstant;
import com.skybooking.eventservice.v1_0_0.ui.model.request.sms.*;
import com.skybooking.eventservice.v1_0_0.ui.model.response.S3UploadRS;
import com.skybooking.eventservice.v1_0_0.util.email.EmailBean;
import com.skybooking.eventservice.v1_0_0.util.pdf.PdfBean;
import com.skybooking.eventservice.v1_0_0.util.sms.SmsBean;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.skybooking.eventservice.constant.SmsKey.*;

@Service
public class SkyPointSmsIP implements SkyPointSmsSV {

    @Autowired
    private SmsBean smsBean;

    @Autowired
    private PdfBean pdfBean;

    @Autowired
    private Configuration configuration;

    @Autowired
    private EmailBean emailBean;

    @Override
    public S3UploadRS sendTopUpSms(SkyPointTopUpSuccessSmsRQ skyPointTopUpSuccessSmsRQ) {

        Map<String, Object> smsData = smsBean.smsData(skyPointTopUpSuccessSmsRQ.getPhone(), SKY_POINT_TOP_UP_SMS);
        skyPointTopUpSuccessSmsRQ.setEmail(skyPointTopUpSuccessSmsRQ.getPhone());

        List<File> fileList = new ArrayList<>();

        Map<String, Object> pdfData = new HashMap<>();
        pdfData.put("data", skyPointTopUpSuccessSmsRQ);
        pdfData.put("pdfTemplate", "top_up");
        pdfData.put("label_topUp", emailBean.dataPdfTemplate("api_receipt_top_up_pdf"));
        S3UploadRS s3UploadRS = new S3UploadRS();
        try {
            File file = pdfBean.pdfRender(configuration, pdfData);

            s3UploadRS = pdfBean.uploadPdfTos3bucket(file);

            fileList.add(file);
            fileList.forEach(File::delete);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        smsBean.sendSms(smsData);

        return s3UploadRS;

    }

    @Override
    public void sendTopUpFailed(SkyPointTopUpFailedSmsRQ skyPointTopUpFailedSmsRQ) {
        Map<String, Object> smsData = smsBean.smsData(skyPointTopUpFailedSmsRQ.getPhone(),
                SKY_POINT_TOP_UP_FAILED_SMS);
        smsBean.sendSms(smsData);
    }

    @Override
    public void sendEarnedSms(SkyPointEarnSmsRQ skyPointEarnSmsRQ) {

        Map<String, Object> smsData = smsBean.smsData(skyPointEarnSmsRQ.getPhone(), SKY_POINT_EARNED_SMS);

        if (skyPointEarnSmsRQ.getTransactionFor().equalsIgnoreCase(TransactionForConstant.FLIGHT)) {
            smsData = emailBean.dataSMSTemplate(API_PRODUCT_FLIGHT, smsData);
        } else {
            smsData = emailBean.dataSMSTemplate(API_PRODUCT_HOTEL, smsData);
        }
        smsData.put("amount", skyPointEarnSmsRQ.getAmount());
        smsData.put("bookingId", skyPointEarnSmsRQ.getBookingId());
        smsBean.sendSms(smsData);

    }

    @Override
    public void sendRedeemSms(SkyPointRedeemSmsRQ skyPointRedeemSmsRQ) {

        Map<String, Object> smsData = smsBean.smsData(skyPointRedeemSmsRQ.getPhone(),
                SKY_POINT_REDEEM_SMS);
        if (skyPointRedeemSmsRQ.getTransactionFor().equalsIgnoreCase(TransactionForConstant.FLIGHT)) {
            smsData = emailBean.dataSMSTemplate(API_PRODUCT_FLIGHT, smsData);
        } else {
            smsData = emailBean.dataSMSTemplate(API_PRODUCT_HOTEL, smsData);
        }

        smsData.put("bookingId", skyPointRedeemSmsRQ.getBookingId());
        smsBean.sendSms(smsData);

    }

    @Override
    public void sendRefundSms(SkyPointRefundSmsRQ skyPointRefundSmsRQ) {

        Map<String, Object> smsData = smsBean.smsData(skyPointRefundSmsRQ.getPhone(),
                SKY_POINT_REFUND_SMS);
        smsBean.sendSms(smsData);

    }
}
