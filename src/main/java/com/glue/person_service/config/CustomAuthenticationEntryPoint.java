package com.glue.person_service.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glue.person_service.domain.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ErrorResponse error = ErrorResponse.builder()
                        .statusCode(HttpServletResponse.SC_UNAUTHORIZED)
                        .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .details("Wrong credentials used to authenticate to the application")
                .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(mapper.writeValueAsString(error));
    }


}


