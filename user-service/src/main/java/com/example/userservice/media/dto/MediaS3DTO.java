package com.example.userservice.media.dto;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.userservice.media.statics.StorageAccessType;
import lombok.Data;

@Data
public class MediaS3DTO {
    public final static String TEXT_PLAIN = "text/plain";
    public final static String APPLICATION_PDF = "application/pdf";

    private byte[] bytes;
    private String folderName;
    private String originalFileName;
    private String fileName;
    private String src;
    private boolean publicRead;
    private String contentType;

    public ObjectMetadata toObjectMetadata() {
        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentLength(bytes.length);
        metaData.setContentType(contentType);
        return metaData;
    }

    public CannedAccessControlList getS3Access() {
        return publicRead ? CannedAccessControlList.PublicRead : CannedAccessControlList.Private;
    }

    public String getAccessTypeFolder() {
        return publicRead ? StorageAccessType.PUBLIC.getNameLower() : StorageAccessType.PRIVATE.getNameLower();
    }
}
