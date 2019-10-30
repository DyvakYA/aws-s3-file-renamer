package com.dyvak.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.dyvak.configuration.conditional.ConditionalOnAmazonPropertiesPresent;
import com.dyvak.configuration.conditional.ConditionalOnApplicationPropertiesPresent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {AmazonCredentialsProperties.class, ApplicationProperties.class})
public class AmazonConfiguration {

    @Autowired
    private AmazonCredentialsProperties properties;

    @Bean
    @ConditionalOnAmazonPropertiesPresent
    @ConditionalOnApplicationPropertiesPresent
    public AmazonS3 amazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey());
        return new AmazonS3Client(credentials);
    }
}
