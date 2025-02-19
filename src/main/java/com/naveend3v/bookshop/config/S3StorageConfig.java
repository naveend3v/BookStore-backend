package com.naveend3v.bookshop.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class S3StorageConfig {
    @Value("${aws.credentials.access-key-id}")
    private String accessKeyID;

    @Value("${aws.credentials.secret-access-key}")
    private String secretAccessKey;

    @Value("${aws.bucket.region}")
    private String region;

    @Bean
    public AmazonS3 s3Client(){
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyID,secretAccessKey);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}
