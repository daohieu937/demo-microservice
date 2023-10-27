package com.example.userservice.media.statics;

import java.util.Locale;

public enum StorageType {
    TEMPORARY, PERMANENT;

    public String getNameLower() {
        return toString().toLowerCase(Locale.ROOT);
    }
}
