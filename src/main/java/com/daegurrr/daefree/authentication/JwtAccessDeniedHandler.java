package com.daegurrr.daefree.authentication;

import com.daegurrr.daefree.exception.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        ExceptionResponse exception = ExceptionResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .message("접근 권한이 없는 요청입니다.")
                .build();
        objectMapper.writeValue(response.getOutputStream(), exception);
    }
}
