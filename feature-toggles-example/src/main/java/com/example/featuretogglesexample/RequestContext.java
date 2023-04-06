package com.example.featuretogglesexample;


import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Data
@Component
@RequestScope
public class RequestContext {

    private String userId;

}
