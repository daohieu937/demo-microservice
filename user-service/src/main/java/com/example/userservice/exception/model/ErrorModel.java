package com.example.userservice.exception.model;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorModel {
    private int statusCode;
    private List<String> messages;
    private String errorCode;
    private Object additionalData;


    public ErrorModel(int statusCode, List<String> messages, String errorCode){
        this(statusCode, messages);
        this.errorCode = errorCode;
    }
    public ErrorModel(int statusCode, List<String> messages){
        this.statusCode = statusCode;
        this.messages = messages;
    }

    public ErrorModel(int statusCode, String message, String errorCode){
        this(statusCode, message);
        this.errorCode = errorCode;
    }
    public ErrorModel(int statusCode, String message){
        this.statusCode = statusCode;
        this.messages = Collections.singletonList(message);
    }

}
