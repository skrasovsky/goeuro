package com.goeuro.config;

import com.goeuro.core.api.API;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(
        basePackages = {"com.goeuro.service", "com.goeuro.core", "com.goeuro.command"},
        includeFilters = @ComponentScan.Filter(API.class))
@PropertySource({"classpath:goeuro.properties", "classpath:cmd.properties"})
public class ApplicationConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
