package com.example.featuretogglesexample.service;

import com.example.featuretogglesexample.configuration.UnleashProperties;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.finn.unleash.Unleash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnleashServiceImpl implements UnleashService {

    private final RestTemplate unleashApiClient;
    private final Unleash unleash;
    private final UnleashProperties unleashProperties;
    @Value("${available-feature-flags}")
    private String availableFLags;
    private String adminToken;

    @Override
    public boolean createFlag(String toggleName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, adminToken);
        String requestBody = "{\n" +
            "  \"name\": \"" + toggleName + "\"\n" +
            "}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        log.info("request body: {}", requestBody);
        log.info("headers: {}", headers);
        log.info("URL: {}", unleashURL());
        ResponseEntity<String> response = unleashApiClient.postForEntity(unleashURL(), entity, String.class);
        return response.getStatusCode().value() == 201;
    }

    @Override
    public boolean archiveFlag(String flagName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, adminToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = unleashApiClient.exchange(unleashURL() + "/" + flagName, HttpMethod.DELETE, entity, String.class);
        return response.getStatusCode().value() == 202;
    }

    @Override
    public void createDefaultFlags() {
        List<String> existingFlags = unleash.more().getFeatureToggleNames();
        List<String> flags = Arrays.stream(availableFLags.split(","))
            .filter(flag -> !existingFlags.contains(flag))
            .toList();
        flags.forEach(this::createFlag);

    }

    @Override
    public void setAdminToken(String token) {
        adminToken = token;
    }

    private String unleashURL() {
        return unleashProperties.getApiUrl() + "admin/projects/" + unleashProperties.getAppName() + "/features";
    }
}
