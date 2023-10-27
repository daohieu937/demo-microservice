package com.example.userservice.media.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.userservice.config.StorageConfiguration;
import com.example.userservice.media.dto.MediaS3DTO;
import com.example.userservice.media.exception.InternalErrorException;
import com.example.userservice.media.statics.ResponseMessage;
import com.example.userservice.media.statics.StorageType;
import com.example.userservice.media.utils.DateUtils;
import com.example.userservice.media.utils.StorageUtils;
import com.example.userservice.media.utils.ThrowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class StorageService {
    @Autowired
    private AmazonS3 s3Client;

    public Optional<String> uploadToS3(MediaS3DTO mediaS3DTO, StorageType storageType) {
        try {
            // Upload a file.
            ObjectMetadata metaData = mediaS3DTO.toObjectMetadata();
            InputStream inputStream = new ByteArrayInputStream(mediaS3DTO.getBytes());
            String key = StorageUtils.buildKey(storageType, mediaS3DTO);
            s3Client.putObject(
                    new PutObjectRequest(StorageConfiguration.BUCKET_NAME, key, inputStream, metaData).withCannedAcl(mediaS3DTO.getS3Access())
            );
            return Optional.of(StorageUtils.buildFileUrl(key));
        } catch (SdkClientException ex) {
            ThrowableUtils.printError(ex);
        }
        return Optional.empty();
    }

    public String presign(String fileUrl) {
        if (fileUrl != null) {
            String objectKey = fileUrl.replace(StorageConfiguration.ENDPOINT, "");
            Date expiration = DateUtils.getDateAfter(StorageConfiguration.EXPIRE_TIME_IN_SECOND);
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(StorageConfiguration.BUCKET_NAME, objectKey)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            return url.toString();
        }
        return null;
    }

    public ByteArrayOutputStream downloadFile(String fileUrl) {
        try {
            fileUrl = fileUrl.trim().replace(StorageConfiguration.ENDPOINT, "");
            S3Object s3object = s3Client.getObject(new GetObjectRequest(StorageConfiguration.BUCKET_NAME, fileUrl));

            InputStream is = s3object.getObjectContent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            return outputStream;
        } catch (IOException | AmazonClientException e) {
            ThrowableUtils.printError(e);
            throw new InternalErrorException(ResponseMessage.NOT_DOWNLOAD_FILE);
        }
    }
}

