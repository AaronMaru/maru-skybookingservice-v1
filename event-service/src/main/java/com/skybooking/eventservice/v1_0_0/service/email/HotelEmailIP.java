package com.skybooking.eventservice.v1_0_0.service.email;

import com.skybooking.eventservice.constant.AttachmentConstant;
import com.skybooking.eventservice.v1_0_0.io.entity.AttachmentEntity;
import com.skybooking.eventservice.v1_0_0.io.repository.AttachmentRP;
import com.skybooking.eventservice.v1_0_0.ui.model.request.hotel.PaymentInfoRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.request.hotel.PaymentSuccessRQ;
import com.skybooking.eventservice.v1_0_0.ui.model.response.S3UploadRS;
import com.skybooking.eventservice.v1_0_0.util.email.EmailBean;
import com.skybooking.eventservice.v1_0_0.util.header.HeaderBean;
import com.skybooking.eventservice.v1_0_0.util.pdf.PdfBean;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.skybooking.eventservice.constant.EmailKey.HOTEL_PAYMENT_INFO;
import static com.skybooking.eventservice.constant.EmailKey.HOTEL_PAYMENT_SUCCESS;

@Service
public class HotelEmailIP implements HotelEmailSV {

    @Autowired
    private EmailBean emailBean;

    @Autowired
    private PdfBean pdfBean;

    @Autowired
    private Configuration configuration;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private AttachmentRP attachmentRP;

    @Autowired
    private Environment environment;

    @Override
    public void paymentSuccess(PaymentSuccessRQ paymentSuccessRQ) {

        Map<String, Object> mailData = emailBean.hotelMailData(paymentSuccessRQ.getEmail(), HOTEL_PAYMENT_SUCCESS);

        mailData.put("data", paymentSuccessRQ);

        emailBean.email(mailData, null);
    }

    @Override
    public void paymentInfo(PaymentInfoRQ paymentInfoRQ) {

        Map<String, Object> mailData = emailBean.hotelMailData(paymentInfoRQ.getEmail(), HOTEL_PAYMENT_INFO);

        List<File> fileList = new ArrayList<>();
        Map<String,  File> fileMap = new HashMap<>();

        Map<String, Object> pdfData = new HashMap<>();
        pdfData.put("pdfTemplate", paymentInfoRQ.getType());
        pdfData.put("data", paymentInfoRQ);

        try {
            if (paymentInfoRQ.getType().equals("all")) {

                pdfData.put("label_voucher", emailBean.dataPdfTemplate("api_voucher_hotel_pdf"));
                pdfData.put("label_receipt", emailBean.dataPdfTemplate("api_receipt_hotel_pdf"));

                pdfData.put("pdfTemplate", "e-receipt");
                File receipt = pdfBean.pdfRender(configuration, pdfData);

                fileList.add(receipt);
                pdfData.put("pdfTemplate", "voucher");
                File voucher = pdfBean.pdfRender(configuration, pdfData);
                fileList.add(voucher);

                fileMap.put(AttachmentConstant.SKYHOTEL_RECEIPT, receipt);
                fileMap.put(AttachmentConstant.SKYHOTEL_VOUCHER, voucher);

            } else {
                File file = null;
                if (paymentInfoRQ.getUrl() != null) {
                    file = pdfBean.readPdfUrl(paymentInfoRQ.getUrl(), pdfData.get("pdfTemplate").toString());
                } else {

                    pdfData.put("pdfTemplate", paymentInfoRQ.getType());

                    String type = "";
                    if (paymentInfoRQ.getType().equals("voucher")) {
                        type = AttachmentConstant.SKYHOTEL_VOUCHER;
                        pdfData.put("label_voucher", emailBean.dataPdfTemplate("api_voucher_hotel_pdf"));
                    }
                    if (paymentInfoRQ.getType().equals("e-receipt")) {
                        type = AttachmentConstant.SKYHOTEL_RECEIPT;
                        pdfData.put("label_receipt", emailBean.dataPdfTemplate("api_receipt_hotel_pdf"));
                    }

                    file = pdfBean.pdfRender(configuration, pdfData);
                    fileMap.put(type, file);
                }

                fileList.add(file);
            }

            if (fileMap.size() > 0) {
                saveAttachment(fileMap, paymentInfoRQ.getBookingCode());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        emailBean.email(mailData, fileList);

    }

    private void saveAttachment(Map<String,  File> fileMap, String reference) {

        fileMap.forEach( (k, v) -> {

            String urlUpload = "spring.awsImage.skyhotel.voucher";
            if (k.equals(AttachmentConstant.SKYHOTEL_RECEIPT)) {
                urlUpload = "spring.awsImage.skyhotel.receipt";
            }

            S3UploadRS fileInfo = pdfBean.uploadPdfTos3bucketHotel(v, urlUpload);
            AttachmentEntity attachment = new AttachmentEntity();
            attachment.setReference(reference);
            attachment.setLangCode(headerBean.getLocalization());
            attachment.setName(fileInfo.getFile());
            attachment.setPath(fileInfo.getPath());
            attachment.setType(k);

            attachmentRP.save(attachment);

        });

    }

}
