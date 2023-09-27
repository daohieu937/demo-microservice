package com.example.userservice.exception;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends BaseCustomException {

    public UnprocessableEntityException(String... messages) {
        super(messages);
    }


    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
