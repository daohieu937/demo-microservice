package com.example.userservice.media.utils;

import com.example.userservice.config.StorageConfiguration;
import com.example.userservice.media.dto.MediaS3DTO;
import com.example.userservice.media.statics.StorageType;
import org.apache.commons.lang3.StringUtils;

public class StorageUtils {
    public static String buildFileUrl(String key) {
        return PathUtils.join(StorageConfiguration.ENDPOINT, key);
    }

    public static String buildKey(StorageType storageType, MediaS3DTO mediaS3DTO) {
        if (StringUtils.isBlank(mediaS3DTO.getFolderName())) {
            return PathUtils.join(mediaS3DTO.getAccessTypeFolder(), storageType.getNameLower(), mediaS3DTO.getFileName());
        }
        return PathUtils.join(mediaS3DTO.getAccessTypeFolder(), storageType.getNameLower(), mediaS3DTO.getFolderName(), mediaS3DTO.getFileName());
    }
}

