package com.example.featuretogglesexample.service;

public interface UnleashService {

    boolean createFlag(String toggleName);

    boolean archiveFlag(String flagName);

    void createDefaultFlags();

    void setAdminToken(String token);

}
