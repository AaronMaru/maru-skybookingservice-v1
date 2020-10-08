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
     * Getting a url part amazon of photo
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param fileName String
     * @return String
     */
    public String partUrl(String fileName) {

        fileName = (fileName != null) ? fileName : "default.png";
        return environment.getProperty("spring.aws.awsUrl.upload") + fileName;
    }
}