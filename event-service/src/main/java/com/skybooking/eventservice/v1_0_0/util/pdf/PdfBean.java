package com.skybooking.eventservice.v1_0_0.util.pdf;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;
import com.skybooking.eventservice.v1_0_0.ui.model.response.S3UploadRS;
import com.skybooking.eventservice.v1_0_0.util.general.ApiBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

public class PdfBean {

    private Logger logger = LoggerFactory.getLogger(PdfBean.class);

    @Autowired
    private Environment environment;

    @Autowired
    private ApiBean apiBean;

    @Autowired
    private AmazonS3 s3client;

    public File pdfRender(Configuration configuration, Map<String, Object> pdfData)
            throws IOException, InterruptedException {

        Pdf pdf = new Pdf();
        pdf.addParam(new Param("--encoding", "UTF-8"));
        String fileName = pdfData.get("pdfTemplate") + "-" + new Timestamp(System.currentTimeMillis()).getTime() + ".pdf";
        ClassPathResource resource = new ClassPathResource(fileName);
        File file = new File(resource.getPath());

        try {
            Template template = configuration.getTemplate("pdf/" + pdfData.get("pdfTemplate").toString() + ".ftl");
            String htmlTemplate = FreeMarkerTemplateUtils.processTemplateIntoString(template, pdfData);

            pdf.addPageFromString(htmlTemplate);
            pdf.saveAs(fileName);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return file;
    }

    public File readPdfUrl(String urlPdf, String template) throws IOException {

        URL url = new URL(urlPdf);
        InputStream in = url.openStream();

        String fileName = template + "-" + new Timestamp(System.currentTimeMillis()).getTime() + ".pdf";
        ClassPathResource resource = new ClassPathResource(fileName);
        File file = new File(resource.getPath());

        FileOutputStream fos = new FileOutputStream(new File(fileName));

        //Reading from resource and writing to file...
        int length = -1;
        byte[] buffer = new byte[1024];// buffer for portion of data from connection
        while ((length = in.read(buffer)) > -1) {
            fos.write(buffer, 0, length);
        }
        fos.close();
        in.close();

        return file;

    }

    public S3UploadRS uploadPdfTos3bucket(File file) {
        return uploadPdfTos3bucketAll(file, "spring.awsImage.bucket", "spring.awsImage.skyPoint.topUpUrl");
    }

    public S3UploadRS uploadPdfTos3bucketHotel(File file, String urlUpload) {
        return uploadPdfTos3bucketAll(file, "spring.awsImage.bucketHotel", urlUpload);
    }

    private S3UploadRS uploadPdfTos3bucketAll(File file, String bucket, String urlUpload) {
        LocalDateTime now = LocalDateTime.now();
        String yearMonth = now.getYear() + "/" + now.getMonthValue();

        String generateFileName = apiBean.generateFileName(null) + ".pdf";

        try {
            s3client.putObject(new PutObjectRequest(environment.getProperty(bucket)
                    + environment.getProperty(urlUpload) + yearMonth, generateFileName, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonServiceException e) {
            logger.error("Amazon Upload fail " + e);
        }

        return new S3UploadRS(generateFileName, yearMonth);
    }





}
