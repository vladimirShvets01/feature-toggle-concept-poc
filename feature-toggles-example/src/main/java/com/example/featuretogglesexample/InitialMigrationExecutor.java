package com.example.featuretogglesexample;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialMigrationExecutor implements CommandLineRunner {

    private final DatabaseUnleashSynchronization databaseUnleashSynchronization;

    @Override
    public void run(String... args) throws Exception {
//        databaseUnleashSynchronization.performStartUpMigrations();
    }
}
