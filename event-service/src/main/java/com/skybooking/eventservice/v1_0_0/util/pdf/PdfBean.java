package com.skybooking.eventservice.v1_0_0.util.pdf;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PdfBean {

    public File pdfRender(Configuration configuration, Map<String, Object> pdfData)
            throws IOException, InterruptedException {

        Pdf pdf = new Pdf();
        pdf.addParam(new Param("--encoding", "UTF-8"));

        ClassPathResource resource = new ClassPathResource("topUp.pdf");
        File file = new File(resource.getPath());

        try {
            Template template = configuration.getTemplate("pdf/topUp.ftl");
            String htmlTemplate = FreeMarkerTemplateUtils.processTemplateIntoString(template, pdfData);

            pdf.addPageFromString(htmlTemplate);
            pdf.saveAs("topUp.pdf");
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return file;
    }

}
