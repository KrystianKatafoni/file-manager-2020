package com.katafoni.filemanager.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoDto {
    private Long id;
    private String name;
    private long size;
    private String extension;
    private LocalDateTime creationDate;
    private String createdBy;
}
