package com.katafoni.filemanager.file;

import com.katafoni.filemanager.file.dto.FileDto;
import com.katafoni.filemanager.file.dto.FileInfoDto;

import java.util.List;


public interface FileMapper {

    FileInfoDto fileEntityToFileInfoDto(FileEntity fileEntity);

    List<FileInfoDto> fileEntitiesToFileInfoDtos(List<FileEntity> fileEntities);

    FileDto fileEntityAndFileBytesToFileDto(FileEntity fileEntity, byte[] file);

    FileEntity fileDtoToFileEntity(FileDto fileDto);

}
