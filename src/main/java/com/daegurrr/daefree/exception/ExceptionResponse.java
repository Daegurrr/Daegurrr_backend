package com.daegurrr.daefree.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionResponse {
    private int code;
    private String message;
    private HttpStatus data;

    @Builder
    public ExceptionResponse(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.message = message;
        this.data = httpStatus;
    }
}
