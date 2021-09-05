package com.alkemy.java.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
    private ZonedDateTime timestamp;

    public ApiException(int code, HttpStatus httpStatus, String message, ZonedDateTime timestamp) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
