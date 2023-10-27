package com.example.userservice.media.service;

import com.example.userservice.media.domain.File;
import com.example.userservice.media.dto.MediaS3DTO;
import com.example.userservice.media.exception.NotFoundException;
import com.example.userservice.media.repository.FileRepository;
import com.example.userservice.media.statics.ResponseMessage;
import com.example.userservice.media.statics.StorageType;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private StorageService storageService;

    public Optional<File> uploadFile(MediaS3DTO mediaS3DTO, StorageType storageType) {
        return storageService.uploadToS3(mediaS3DTO, storageType)
                .map(url -> File.fromMediaS3DTO(mediaS3DTO).withUrl(url))
                .map(file -> fileRepository.save(file))
                .map(file -> file.withUrl(storageService.presign(file.getUrl())));
    }

    public Optional<File> uploadSignatures(MediaS3DTO mediaS3DTO, StorageType storageType) {
        return storageService.uploadToS3(mediaS3DTO, storageType)
                .map(url -> File.fromMediaS3DTO(mediaS3DTO).withUrl(url))
                .map(file -> fileRepository.save(file));
    }

    public String getFileUrlPresigned(UUID fileId) {
        if (fileId == null) {
            throw new NotFoundException(ResponseMessage.FILE_NOT_FOUND);
        }
        return fileRepository.findById(fileId).map(file -> storageService.presign(file.getUrl()))
                .orElseThrow(() -> new NotFoundException(ResponseMessage.FILE_NOT_FOUND));
    }

    public ByteArrayOutputStream downloadFile(UUID fileId) {
        if (fileId == null) {
            throw new NotFoundException(ResponseMessage.FILE_NOT_FOUND);
        }
        return fileRepository.findById(fileId).map(file -> storageService.downloadFile(file.getUrl()))
                .orElseThrow(() -> new NotFoundException(ResponseMessage.FILE_NOT_FOUND));
    }

    public ByteArrayOutputStream downloadFile(String url) {
        return storageService.downloadFile(url);
    }

    public String downloadFileWithBase64Format(UUID fileId) {
        ByteArrayOutputStream file = this.downloadFile(fileId);
        return Base64Utils.encodeToString(file.toByteArray());
    }

    public String downloadFileWithBase64Format(String url) {
        ByteArrayOutputStream file = this.downloadFile(url);
        return Base64Utils.encodeToString(file.toByteArray());
    }

    public Pair<File, ByteArrayOutputStream> downloadFileWithInfo(UUID fileId) {
        if (fileId == null) {
            throw new NotFoundException(ResponseMessage.FILE_NOT_FOUND);
        }
        return fileRepository.findById(fileId).map(file -> Pair.of(file, storageService.downloadFile(file.getUrl())))
                .orElseThrow(() -> new NotFoundException(ResponseMessage.FILE_NOT_FOUND));
    }

    @SneakyThrows
    public static byte[] zipFiles(Pair<File, ByteArrayOutputStream>... filePairs) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(bos);
        for (Pair<File, ByteArrayOutputStream> filePair : filePairs) {
            zipOut.putNextEntry(new ZipEntry(filePair.getLeft().getName()));
            zipOut.write(filePair.getRight().toByteArray());
        }
        zipOut.close();
        return bos.toByteArray();
    }

    @SneakyThrows
    public static byte[] zipFiles(List<Pair<String, byte[]>> filePairs) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(bos);
        for (Pair<String, byte[]> filePair : filePairs) {
            zipOut.putNextEntry(new ZipEntry(filePair.getLeft()));
            zipOut.write(filePair.getRight());
        }
        zipOut.close();
        return bos.toByteArray();
    }

    public List<File> findByIds(List<UUID> ids) {
        return fileRepository.findByIdIn(ids);
    }

    public List<File> findByIdsAndPresign(List<UUID> ids) {
        return fileRepository.findByIdIn(ids).stream().map(file -> file.withUrl(storageService.presign(file.getUrl()))).toList();
    }

    public Optional<File> findById(UUID fileId) {
        return fileRepository.findById(fileId);
    }
}
