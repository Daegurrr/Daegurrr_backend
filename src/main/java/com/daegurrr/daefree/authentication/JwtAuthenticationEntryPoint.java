package com.daegurrr.daefree.authentication;

import com.daegurrr.daefree.exception.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        if (request.getAttribute("exception") != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            ExceptionResponse exception = ExceptionResponse.builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message(request.getAttribute("exception").toString())
                    .build();

            objectMapper.writeValue(response.getOutputStream(), exception);
        }

    }
}
