package com.katafoni.filemanager.file;

import org.mapstruct.Mapper;

import java.util.List;


public interface FileMapper {
    FileDto fileEntityToFileDto(FileEntity fileEntity);
    FileEntity fileDtoToFileEntity(FileDto fileDto);
    List<FileDto> fileEntitiesToFileDtos(List<FileEntity> fileEntities);
}
