package com.example.userservice.media.exception;

import com.example.userservice.exception.BaseCustomException;
import com.example.userservice.media.statics.ResponseMessage;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseCustomException {
    public NotFoundException(ResponseMessage responseMessage) {
        super(responseMessage.getMessage());
        this.setErrorCode(responseMessage.getCode());
    }

    public NotFoundException(ResponseMessage responseMessage, Object... substitutes) {
        super(responseMessage.getMessage(substitutes));
        this.setErrorCode(responseMessage.getCode());
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
