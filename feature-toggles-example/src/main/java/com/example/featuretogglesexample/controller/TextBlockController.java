package com.example.featuretogglesexample.controller;

import com.example.featuretogglesexample.DatabaseUnleashSynchronization;
import com.example.featuretogglesexample.configuration.TextBlockAppConfiguration;
import com.example.featuretogglesexample.repository.TextBlocksRepository;
import com.example.featuretogglesexample.service.DbService;
import com.github.javafaker.Faker;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/lines")
public class TextBlockController {

    private final TextBlocksRepository textBlocksRepository;
    private final TextBlockAppConfiguration flagAppConfiguration;
    private final DatabaseUnleashSynchronization databaseUnleashSynchronization;
    private final DbService dbService;

    @GetMapping("/feature")
    public ResponseEntity<?> getTextBlocks() {
        if (flagAppConfiguration.featureFlag2()) {
            var lines = textBlocksRepository.getTextBlock(true);
            return ResponseEntity.ok().body(lines);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/single")
    public ResponseEntity<String> getLine(@RequestParam boolean flag5) {
        if (flag5) {
            return ResponseEntity.ok("Random name from middleware layer: " + Faker.instance().name().nameWithMiddle());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/init")
    public ResponseEntity<Void> initDB() {
        databaseUnleashSynchronization.performStartUpMigrations();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/liquibase")
    public ResponseEntity<Void> updateFlagsForDB() {
        databaseUnleashSynchronization.updateDatabase();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/schema")
    public ResponseEntity<String> getDbSchema() {
        String columns = String.join(", ", dbService.getListOfColumns("user"));
        return ResponseEntity.ok(columns);
    }

}
