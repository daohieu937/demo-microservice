package com.example.userservice.exception;

import com.example.userservice.exception.model.ErrorModel;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FeignResponseException extends BaseCustomException {

    private HttpStatus status;

    private ErrorModel errorModel;

    public FeignResponseException(ErrorModel errorModel) {
        this.status = HttpStatus.valueOf(errorModel.getStatusCode());
        this.errorModel = errorModel;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.status;
    }
}

