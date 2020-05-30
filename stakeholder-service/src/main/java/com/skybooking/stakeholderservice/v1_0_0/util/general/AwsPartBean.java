package com.skybooking.stakeholderservice.v1_0_0.util.general;

import com.skybooking.stakeholderservice.constant.AwsPartConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class AwsPartBean {

    @Autowired
    private Environment environment;

    public String partUrl(String typeSky, String fileName) {

        String partUrl = "";
        fileName = (fileName != null) ? fileName : "default.png";

        switch (typeSky) {
            case AwsPartConstant.SKYUSER_PROFILE_MEDIUM :
                partUrl = environment.getProperty("spring.awsImageUrl.profile.url_larg") + fileName;
                break;
            case AwsPartConstant.SKYUSER_PROFILE_SMALL :
                partUrl = environment.getProperty("spring.awsImageUrl.profile.url_small") + "thumb_" + fileName;
                break;
            case AwsPartConstant.COMPANY_PROFILE_MEDIUM :
                partUrl = environment.getProperty("spring.awsImageUrl.companyProfile") + "medium/" + fileName;
                break;
            case AwsPartConstant.COMPANY_ITENERARY :
                partUrl = environment.getProperty("spring.awsImageUrl.companyProfile") + "origin/" + fileName;
                break;
            case AwsPartConstant.COMPANY_LICENSE :
                partUrl = environment.getProperty("spring.awsImageUrl.companyLicense") + fileName;
                break;
            case AwsPartConstant.CONTENT :
                partUrl = environment.getProperty("spring.awsImageUrl.awsContent");
                break;
            default:
                partUrl = "No url for this image";
                break;
        }

        return partUrl;

    }

}
