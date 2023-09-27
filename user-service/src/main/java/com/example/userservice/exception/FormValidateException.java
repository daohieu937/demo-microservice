package com.example.userservice.exception;

import org.springframework.http.HttpStatus;

public class FormValidateException extends BaseCustomException{
    public FormValidateException(String... messages) {
        super(messages);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
