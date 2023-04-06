package com.example.featuretogglesexample.controller;

import com.example.featuretogglesexample.model.dto.FlagCreationRequest;
import com.example.featuretogglesexample.service.UnleashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/unleash/flag")
public class UnleashController {

    private final UnleashService unleashService;

    @PostMapping
    public ResponseEntity<Void> createFeatureFLag(@RequestBody FlagCreationRequest request) {
        var created = unleashService.createFlag(request.flagName());
        if (created) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{flagName}")
    public ResponseEntity<Void> removeFeatureToggle(@PathVariable String flagName) {
        unleashService.archiveFlag(flagName);
        return ResponseEntity.ok().build();
    }

}
