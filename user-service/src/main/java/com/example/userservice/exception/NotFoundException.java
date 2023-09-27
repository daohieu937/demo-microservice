package com.example.userservice.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class NotFoundException extends BaseCustomException {
    public NotFoundException() {
        super(new String[]{"Object not found"});
    }

    public NotFoundException(String message) {
        super(new String[]{message});
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
