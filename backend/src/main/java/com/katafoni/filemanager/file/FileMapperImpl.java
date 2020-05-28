package com.katafoni.filemanager.file;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileMapperImpl implements FileMapper{
    @Override
    public FileDto fileEntityToFileDto(FileEntity fileEntity) {
        FileDto fileDto = FileDto.builder()
                .id(fileEntity.getId())
                .createdBy(fileEntity.getCreatedBy())
                .creationDate(fileEntity.getCreationDate())
                .name(fileEntity.getName())
                .path(fileEntity.getPath())
                .build();
        return fileDto;
    }

    @Override
    public FileEntity fileDtoToFileEntity(FileDto fileDto) {
        return null;
    }

    @Override
    public List<FileDto> fileEntitiesToFileDtos(List<FileEntity> fileEntities) {
        return fileEntities.stream()
                .map(this::fileEntityToFileDto)
                .collect(Collectors.toList());
    }
}
