package com.glue.person_service.exception_handler;

import com.glue.person_service.exception.PersonDoesNotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomExceptionHandlerTest {
    private final CustomExceptionHandler handler = new CustomExceptionHandler();

    @Test
    void handleMissingRequestParameter() {
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("param", "String");
        Map<String, String> result = handler.handleMissingRequestParameter(ex);
        assertEquals(ex.getMessage(), result.get("errorMessage"));
    }

    @Test
    void handleInvalidFields() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "field", "Invalid value");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        Map<String, String> result = handler.handleInvalidFields(ex);
        assertEquals("Invalid value", result.get("errorMessage"));
    }

    @Test
    void processRuntimeException() {
        RuntimeException ex = new RuntimeException("Runtime error");
        Map<String, String> result = handler.processRuntimeException(ex);
        assertEquals("Runtime error", result.get("errorMessage"));
    }

    @Test
    void handlePersonDoesNotExistException() {
        PersonDoesNotExistException ex = new PersonDoesNotExistException("Person not found");
        Map<String, String> result = handler.handlePersonDoesNotExistException(ex);
        assertEquals("Person not found", result.get("errorMessage"));
    }
}