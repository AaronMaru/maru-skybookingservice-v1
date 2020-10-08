package com.skybooking.eventservice.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonClient {

    @Value("${spring.awsImage.key}")
    private String accessKey;
    @Value("${spring.awsImage.secret}")
    private String secretKey;
    @Value("${spring.awsImage.region}")
    private String region;

    @Bean
    public AmazonS3 s3client() {

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
//                .withAccelerateModeEnabled(true)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();

        return s3Client;

    }
}
