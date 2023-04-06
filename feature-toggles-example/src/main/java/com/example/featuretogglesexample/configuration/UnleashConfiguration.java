package com.example.featuretogglesexample.configuration;


import javax.inject.Provider;
import lombok.RequiredArgsConstructor;
import no.finn.unleash.DefaultUnleash;
import no.finn.unleash.Unleash;
import no.finn.unleash.UnleashContextProvider;
import no.finn.unleash.util.UnleashConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(UnleashProperties.class)
public class UnleashConfiguration {

    private final UnleashProperties unleashProperties;
    private final UnleashContextProvider unleashContextProvider;

    @Bean
    public UnleashConfig unleashConfig() {
        return UnleashConfig.builder()
            .appName(unleashProperties.getAppName())
            .instanceId(unleashProperties.getInstanceId())
            .unleashAPI(unleashProperties.getApiUrl())
            .unleashContextProvider(unleashContextProvider)
            .customHttpHeader("Authorization", unleashProperties.getClientSecret())
            .build();
    }

    @Bean
    public Unleash unleash(UnleashConfig unleashConfig) {
        return new DefaultUnleash(unleashConfig);
    }

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Provider<Unleash> unleashProvider(Unleash unleash) {
        return () -> unleash;
    }


    @Bean
    public RestTemplate unleashApiClient() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

}

