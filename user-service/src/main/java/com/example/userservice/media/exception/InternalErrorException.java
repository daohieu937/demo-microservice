package com.example.userservice.media.exception;

import com.example.userservice.exception.BaseCustomException;
import com.example.userservice.media.statics.ResponseMessage;
import org.springframework.http.HttpStatus;

public class InternalErrorException extends BaseCustomException {
    public InternalErrorException(String... messages) {
        super(messages);
    }

    public InternalErrorException(ResponseMessage responseMessage) {
        super(responseMessage.getMessage());
        this.setErrorCode(responseMessage.getCode());
    }

    public InternalErrorException(ResponseMessage responseMessage, Object... substitutes) {
        super(responseMessage.getMessage(substitutes));
        this.setErrorCode(responseMessage.getCode());
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
