package com.skybooking.skyhotelservice.v1_0_0.util.aws;

import com.skybooking.skyhotelservice.constant.AttachmentConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AwsPartCM {

    @Autowired
    private Environment environment;

    public String partUrl(String typeSkyAws, String fileName) {

        String partUrl = "";
        fileName = (fileName != null) ? fileName : "default.png";

        switch (typeSkyAws) {

            case AttachmentConstant.SKYHOTEL_RECEIPT :
                partUrl = environment.getProperty("spring.awsImageUrl.receipt") + fileName;
                break;
            case AttachmentConstant.SKYHOTEL_VOUCHER :
                partUrl = environment.getProperty("spring.awsImageUrl.voucher") + fileName;
                break;
            case AttachmentConstant.HOTEL_MEDIUM :
                partUrl = environment.getProperty("spring.imageUrl.medium") + fileName;
                break;
            default:
                partUrl = "No url for this image";
                break;

        }

        return partUrl;

    }

}
