package com.glue.person_service.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.junit.jupiter.api.Assertions.*;

class WebConfigTest {
    @Test
    void addInterceptors_shouldNotThrow() {
        WebConfig config = new WebConfig();
        InterceptorRegistry registry = new InterceptorRegistry();
        assertDoesNotThrow(() -> config.addInterceptors(registry));
    }
}
