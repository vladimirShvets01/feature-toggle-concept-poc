package com.example.featuretogglesexample;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.finn.unleash.Unleash;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseUnleashSynchronization {

    private final Unleash unleash;
    private final Environment environment;
    private Set<String> availableFlags = new HashSet<>();
    private Set<String> activeFlags = new HashSet<>();

    public void updateDatabase() {
        Set<String> enabledFlags = availableFlags.stream()
            .filter(unleash::isEnabled)
            .collect(Collectors.toSet());
        Set<String> disabledFlags = activeFlags.stream()
            .filter(flag -> !enabledFlags.contains(flag) && activeFlags.contains(flag))
            .collect(Collectors.toSet());
        Set<String> migrationFlags = activeFlags.stream()
                .filter(flag -> !activeFlags.contains(flag))
            .collect(Collectors.toSet());
        addMigrations(migrationFlags);
        rollbackUnusedMigrations(disabledFlags);
        activeFlags.clear();
        activeFlags.addAll(migrationFlags);
    }

    public void performStartUpMigrations() {
        availableFlags = getAvailableFlags();
        Set<String> enabledFlags = availableFlags.stream()
            .filter(unleash::isEnabled)
            .collect(Collectors.toSet());
        addMigrations(enabledFlags);
        activeFlags.addAll(enabledFlags);
    }

    private void rollbackUnusedMigrations(Set<String> disabledFlags) {
        if (disabledFlags.isEmpty()) {
            return;
        }
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            String contexts = String.join(",", disabledFlags);
            processBuilder.command("mvn", "liquibase:rollback", "-Dliquibase.contexts=" + contexts,
                                   "-Dliquibase.rollbackCount=" + disabledFlags.size());
            processBuilder.directory(new File(System.getProperty("user.dir")));
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("Command exited with code " + exitCode);
        } catch (InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addMigrations(Set<String> enabledFlags) {
        if (enabledFlags.isEmpty()) {
            return;
        }
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            String contexts = String.join(",", enabledFlags);
            processBuilder.command("mvn", "liquibase:update", "-Dliquibase.contexts=" + contexts);
            processBuilder.directory(new File(System.getProperty("user.dir")));
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("Command exited with code " + exitCode);
        } catch (InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private Set<String> getAvailableFlags() {
        String[] flags = environment.getProperty("feature-flags").split(",");
        return Set.of(flags);
    }

}
