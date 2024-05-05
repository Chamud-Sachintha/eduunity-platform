package com.eduunity.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidJWTRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidJWTRequestException(String message) {
        super(message);
    }
}
