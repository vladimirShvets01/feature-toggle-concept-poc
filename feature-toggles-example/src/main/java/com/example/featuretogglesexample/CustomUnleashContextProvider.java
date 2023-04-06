package com.example.featuretogglesexample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.finn.unleash.UnleashContext;
import no.finn.unleash.UnleashContextProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;

@Slf4j
@Component
@RequestScope
@RequiredArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CustomUnleashContextProvider implements UnleashContextProvider {

    private final RequestContext requestContext;

    @Override
    public UnleashContext getContext() {
        UnleashContext unleashContext = UnleashContext.builder()
            .userId(requestContext.getUserId())
            .build();
        log.info("unleash context user-id: {}", unleashContext.getUserId());
        return unleashContext;
    }
}
