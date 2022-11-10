package com.rore.demo.exception;

import lombok.Data;

@Data
public class CustomExceptionResponse {

    private final String code;
    private final String message;

}