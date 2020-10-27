package com.skybooking.eventservice.v1_0_0.service.download;

import com.skybooking.eventservice.v1_0_0.ui.model.request.email.SkyPointTopUpSuccessRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.response.S3UploadRS;
import com.skybooking.eventservice.v1_0_0.util.email.EmailBean;
import com.skybooking.eventservice.v1_0_0.util.pdf.PdfBean;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.skybooking.eventservice.constant.EmailKey.SKY_POINT_TOP_UP;

@Service
public class SkyPointDownloadIP implements SkyPointDownloadSV {

    @Autowired
    private PdfBean pdfBean;

    @Autowired
    private EmailBean emailBean;

    @Autowired
    private Configuration configuration;

    @Override
    public S3UploadRS downloadReceipt(SkyPointTopUpSuccessRQ skyPointTopUpSuccessRQ) {

        Map<String, Object> mailData = emailBean.mailData(skyPointTopUpSuccessRQ.getEmail(), skyPointTopUpSuccessRQ.getFullName(),
                skyPointTopUpSuccessRQ.getAmount(), SKY_POINT_TOP_UP);

        mailData.put("earnAmount", skyPointTopUpSuccessRQ.getEarnAmount());

        Map<String, Object> pdfData = new HashMap<>();
        pdfData.put("data", skyPointTopUpSuccessRQ);
        pdfData.put("pdfTemplate", "top_up");
        pdfData.put("label_topUp", emailBean.dataPdfTemplate("api_receipt_top_up_pdf"));

        S3UploadRS s3UploadRS = new S3UploadRS();

        try {
            File file = pdfBean.pdfRender(configuration, pdfData);

            s3UploadRS = pdfBean.uploadPdfTos3bucket(file);

            file.delete();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return s3UploadRS;
    }
}
