package com.example.userservice.media.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.MessageFormat;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    FILE_NOT_FOUND("610001", "File not found"),
    NOT_DOWNLOAD_FILE("610078", "Cannot download file");

    private final String code;
    private final String message;

    public String getMessage(Object... substitutes) {
        return MessageFormat.format(message, substitutes);
    }
}
