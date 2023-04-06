package com.example.featuretogglesexample.configuration;

import lombok.RequiredArgsConstructor;
import no.finn.unleash.Unleash;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TextBlockAppConfiguration {

    private final Unleash unleash;

    public boolean textBlockFunctionalityEnabled() {
        return unleash.isEnabled("DadJokesFunctionalityToggle");
    }

    public boolean textBlockV2Enabled() {
        return unleash.isEnabled("DadJokesModelV2Toggle");
    }

    public boolean featureFlag2() {
        return unleash.isEnabled("feature-flag-2");
    }

    public boolean featureFlag5() {
        return unleash.isEnabled("feature-flag-5");
    }

    public boolean featureFlag6() {
        return unleash.isEnabled("feature-flag-6");
    }

}
