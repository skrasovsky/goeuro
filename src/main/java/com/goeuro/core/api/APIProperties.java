package com.goeuro.core.api;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class APIProperties {

    @NotEmpty
    @Value("${api.version}")
    private String apiVersion;

    @NotEmpty
    @Value("${api.base.url}")
    private String baseUrl;

    @NotEmpty
    @Value("${api.default.locale}")
    private String defaultLocale;

    public String apiVersion() {
        return apiVersion;
    }

    public String baseUrl() {
        return baseUrl;
    }

    public String defaultLocale() {
        return defaultLocale;
    }

}
