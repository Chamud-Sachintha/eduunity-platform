package com.eduunity.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorDetails {
    private int code;
    private String message;
    private String details;
    private String uri;
}
