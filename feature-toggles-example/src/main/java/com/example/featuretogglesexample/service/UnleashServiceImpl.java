package com.example.featuretogglesexample.service;

import com.example.featuretogglesexample.configuration.UnleashProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UnleashServiceImpl implements UnleashService {

    private final RestTemplate unleashApiClient;
    private final UnleashProperties unleashProperties;
    private static final String URL = "http://localhost:4242/api/admin/projects/default/features";

    @Override
    public boolean createFlag(String toggleName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, unleashProperties.getAdminToken());
        String requestBody = "{\n" +
            "  \"name\": \"" + toggleName + "\"\n" +
            "}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = unleashApiClient.postForEntity(URL, entity, String.class);
        return response.getStatusCode().value() == 201;
    }

    @Override
    public boolean archiveFlag(String flagName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, unleashProperties.getAdminToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = unleashApiClient.exchange(URL + "/" + flagName, HttpMethod.DELETE, entity, String.class);
        return response.getStatusCode().value() == 202;
    }
}
