package com.skybooking.skypointservice.v1_0_0.util.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AwsPathCM {

    @Autowired
    private Environment environment;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Getting a url part amazone of photo
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param String typeSky
     * @Param String fileName
     * @Return String
     */
    public String partUrl(String fileName) {

        fileName = (fileName != null) ? fileName : "default.png";
        String partUrl = environment.getProperty("spring.awsUrl.upload") + fileName;

        return partUrl;

    }
}