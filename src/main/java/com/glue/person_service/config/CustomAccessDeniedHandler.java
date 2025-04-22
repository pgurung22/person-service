package com.glue.person_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glue.person_service.domain.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ErrorResponse error = ErrorResponse.builder()
                .statusCode(HttpServletResponse.SC_FORBIDDEN)
                .message(HttpStatus.FORBIDDEN.getReasonPhrase())
                .details("You are unauthorized to perform this action")
                .build();

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(mapper.writeValueAsString(error));
    }
}
