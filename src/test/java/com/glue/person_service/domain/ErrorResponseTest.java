package com.glue.person_service.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {
    @Test
    void testBuilderAndGettersSetters() {
        ErrorResponse error = ErrorResponse.builder()
                .statusCode(403)
                .message("Forbidden")
                .details("You are unauthorized to perform this action")
                .build();
        assertEquals(403, error.getStatusCode());
        assertEquals("Forbidden", error.getMessage());
        assertEquals("You are unauthorized to perform this action", error.getDetails());
    }

    @Test
    void testNoArgsConstructor() {
        ErrorResponse error = new ErrorResponse();
        error.setStatusCode(404);
        error.setMessage("Not Found");
        error.setDetails("Details");
        assertEquals(404, error.getStatusCode());
        assertEquals("Not Found", error.getMessage());
        assertEquals("Details", error.getDetails());
    }
}
