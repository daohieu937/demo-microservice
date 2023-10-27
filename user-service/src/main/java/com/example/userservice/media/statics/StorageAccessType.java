package com.example.userservice.media.statics;

import java.util.Locale;

public enum StorageAccessType {
    PUBLIC, PRIVATE;

    public String getNameLower() {
        return toString().toLowerCase(Locale.ROOT);
    }
}
