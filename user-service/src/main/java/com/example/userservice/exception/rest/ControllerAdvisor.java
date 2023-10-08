package com.example.userservice.exception.rest;

import com.example.userservice.exception.BaseCustomException;
import com.example.userservice.exception.model.ErrorModel;
import io.jsonwebtoken.MalformedJwtException;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        ErrorModel errorModel;
        HttpStatus httpStatus;
        if (ex instanceof BindException) {
            BindException customEx = (BindException) ex;
            httpStatus = HttpStatus.BAD_REQUEST;
            List<String> messages = customEx.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                    .collect(Collectors.toList());
            errorModel = new ErrorModel(httpStatus.value(), messages, null);
        } else if (ex instanceof MaxUploadSizeExceededException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorModel = new ErrorModel(
                    httpStatus.value(),
                    "Kích thước file tối đa là: " + ((MaxUploadSizeExceededException) ex).getMaxUploadSize()
            );
        }
        else if (ex instanceof BaseCustomException) {
            BaseCustomException customEx = (BaseCustomException) ex;
            httpStatus = customEx.getHttpStatus();
            if(customEx.getMessages() != null){
                errorModel = new ErrorModel(httpStatus.value(), customEx.getMessages(), customEx.getErrorCode());
            } else {
                errorModel = new ErrorModel(httpStatus.value(),"null", customEx.getErrorCode());
            }
        }
        else if (ex instanceof HttpMessageNotReadableException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorModel = new ErrorModel(
                    httpStatus.value(),
                    ex.getMessage()
            );
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            errorModel = new ErrorModel(httpStatus.value(), "An error occured");
        }

        return new ResponseEntity<>(errorModel, header(), httpStatus);
    }

    private HttpHeaders header(){
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return customHeader;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorModel> handleMissingRequestHeaderException(MissingRequestHeaderException ex, NativeWebRequest request) {
        val errorMessages = Arrays.asList(
                ex.getMessage()
        );

        ErrorModel errorModel = new ErrorModel(HttpStatus.BAD_REQUEST.value(), errorMessages);
        return new ResponseEntity<>(errorModel, header(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorModel> handleMalformedJwtException(MalformedJwtException ex, NativeWebRequest request) {
        ErrorModel errorModel = new ErrorModel(HttpStatus.FORBIDDEN.value(), "Invalid JWT token");
        return new ResponseEntity<>(errorModel, header(), HttpStatus.FORBIDDEN);
    }


}
