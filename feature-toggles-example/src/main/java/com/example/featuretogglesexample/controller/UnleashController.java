package com.example.featuretogglesexample.controller;

import com.example.featuretogglesexample.model.dto.TextFieldRequest;
import com.example.featuretogglesexample.service.UnleashService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/unleash")
public class UnleashController {

    private final UnleashService unleashService;

    @PostMapping("/flag")
    public ResponseEntity<Void> createFeatureFLag(@RequestBody TextFieldRequest request) {
        var created = unleashService.createFlag(request.fieldName());
        if (created) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/flag/{flagName}")
    public ResponseEntity<Void> removeFeatureToggle(@PathVariable String flagName) {
        unleashService.archiveFlag(flagName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/token")
    public ResponseEntity<Void> initialSetUp(@RequestBody TextFieldRequest textFieldRequest) {
        log.info("Setting admin token");
        unleashService.setAdminToken(textFieldRequest.fieldName());
        log.info("Setting up initial flags");
        unleashService.createDefaultFlags();
        return ResponseEntity.ok().build();
    }

}
