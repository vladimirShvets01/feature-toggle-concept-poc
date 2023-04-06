package com.example.featuretogglesexample;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@RequiredArgsConstructor
public class RequestContextMvcConfigurer implements WebMvcConfigurer {

    private final RequestContextInterceptor requestContextInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(requestContextInterceptor);
    }
}
