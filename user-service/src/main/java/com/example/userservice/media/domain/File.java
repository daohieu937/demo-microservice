package com.example.userservice.media.domain;

import com.example.userservice.media.dto.MediaS3DTO;
import lombok.*;
import org.apache.commons.io.FilenameUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;


@With
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "files")
public class File {
    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String name;
    private Long size;
    private String url;
    private String mimeType;
    private String extension;
    private Date createdDate;

    @PrePersist
    public void prePersist() {
        createdDate = new Date();
    }

    public static File fromMediaS3DTO(MediaS3DTO mediaS3DTO) {
        return File.builder()
                .name(mediaS3DTO.getOriginalFileName())
                .size((long) mediaS3DTO.getBytes().length)
                .mimeType(mediaS3DTO.getContentType())
                .extension(FilenameUtils.getExtension(mediaS3DTO.getFileName()))
                .createdDate(new Date())
                .build();
    }
}
