package com.katafoni.filemanager.file.impl;

import com.katafoni.filemanager.file.*;
import com.katafoni.filemanager.file.dto.FileDto;
import com.katafoni.filemanager.file.dto.FileInfoDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileMapperImpl implements FileMapper {

    @Override
    public FileInfoDto fileEntityToFileInfoDto(FileEntity fileEntity) {

        return FileInfoDto.builder()
                .id(fileEntity.getId())
                .createdBy(fileEntity.getCreatedBy())
                .creationDate(fileEntity.getCreationDate())
                .name(fileEntity.getName())
                .size(fileEntity.getSize())
                .extension(fileEntity.getExtension().getExtension())
                .build();
    }

    @Override
    public List<FileInfoDto> fileEntitiesToFileInfoDtos(List<FileEntity> fileEntities) {

        return fileEntities.stream()
                .map(this::fileEntityToFileInfoDto)
                .collect(Collectors.toList());
    }

    @Override
    public FileDto fileEntityAndFileBytesToFileDto(FileEntity fileEntity, byte[] file) {

        return FileDto.builder()
                .fileInfoDto(this.fileEntityToFileInfoDto(fileEntity))
                .file(file)
                .build();
    }

    @Override
    public FileEntity fileDtoToFileEntity(FileDto fileDto) {
        return null;
    }

}
