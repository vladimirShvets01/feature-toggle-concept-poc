package com.example.featuretogglesexample.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "unleash")
public class UnleashProperties {

    private String appName;
    private String instanceId;
    private String apiUrl;
    private String clientSecret;
    private String adminToken;

}
