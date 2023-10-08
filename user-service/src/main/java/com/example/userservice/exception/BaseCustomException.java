package com.example.userservice.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public abstract class       BaseCustomException extends RuntimeException{
    private List<String> messages;
    private String errorCode;

    public abstract HttpStatus getHttpStatus();

    public BaseCustomException() {
    }

    public BaseCustomException(String... messages) {
        this.messages = Arrays.asList(messages);
    }


    public List<String> getMessages() {
        return this.messages;
    }

    public String getErrorCode() {
        return this.errorCode;
    }


    public void setMessages(final List<String> messages) {
        this.messages = messages;
    }

    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }


}
