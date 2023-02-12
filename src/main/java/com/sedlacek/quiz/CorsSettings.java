package com.sedlacek.quiz;

import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

public class CorsSettings extends CorsConfiguration {
    @Override
    public CorsConfiguration setAllowedOriginPatterns(List<String> allowedOriginPatterns) {
        return super.setAllowedOriginPatterns(allowedOriginPatterns);
    }
}
