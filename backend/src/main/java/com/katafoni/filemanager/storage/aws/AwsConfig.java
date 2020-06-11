package com.katafoni.filemanager.storage.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {
    AwsProperties awsProperties;

    public AwsConfig(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    @Bean
    public AmazonS3 awsS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsProperties.getKeyId(),
                awsProperties.getAccessKey());
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(awsProperties.getRegion()))
                .withCredentials( new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

}
