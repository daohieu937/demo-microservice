package com.example.userservice.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration {
    public static String BUCKET_NAME;
    public static String ENDPOINT;
    public static Long EXPIRE_TIME_IN_SECOND;

    @Value("${aws.access-key}")
    private String accessKey;
    @Value("${aws.secret-key}")
    private String secretKey;
    @Value("${aws.region}")
    private String region;

    @Value("${aws.bucket-name}")
    public void setBucketName(String bucketName) {
        BUCKET_NAME = bucketName;
    }

    @Value("${aws.endpoint}")
    public void setEndpoint(String endpoint) {
        ENDPOINT = StringUtils.strip(endpoint, "/") + "/";
    }

    @Value("${aws.expire-time}")
    public void setBucketName(Long expireTimeInSecond) {
        EXPIRE_TIME_IN_SECOND = expireTimeInSecond;
    }

    @Bean
    public AmazonS3 s3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}
