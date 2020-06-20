package com.katafoni.filemanager.file.impl;

import com.google.common.io.Files;
import com.katafoni.filemanager.common.security.user.UserEntity;
import com.katafoni.filemanager.extension.ExtensionEntity;
import com.katafoni.filemanager.file.dto.FileInfoDto;
import com.katafoni.filemanager.file.exception.FileDoesntExistException;
import com.katafoni.filemanager.file.*;
import com.katafoni.filemanager.extension.util.ExtensionExtractor;
import com.katafoni.filemanager.extension.util.ExtensionValidator;
import com.katafoni.filemanager.file.search.FileSpecification;
import com.katafoni.filemanager.file.search.SearchCriteria;
import com.katafoni.filemanager.common.security.user.UserService;
import com.katafoni.filemanager.storage.FileStorageResponse;
import com.katafoni.filemanager.storage.FileStorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class FileServiceImpl implements FileService {
    private FileRepository fileRepository;
    private FileMapper fileMapper;
    private UserService userService;
    private FileStorageService fileStorageService;
    private ExtensionExtractor extensionExtractor;
    private ExtensionValidator extensionExtensionValidator;
    public FileServiceImpl(FileRepository fileRepository, FileMapper fileMapper, UserService userService,
                           FileStorageService fileStorageService, ExtensionExtractor extensionExtractor,
                           ExtensionValidator extensionExtensionValidator) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.extensionExtractor = extensionExtractor;
        this.extensionExtensionValidator = extensionExtensionValidator;
    }

    @Override
    public Page<FileInfoDto> getFilesInfo(SearchCriteria searchCriteria, Pageable pageable) {
        UserEntity userEntity = this.userService.getCurrentUser();
        searchCriteria.setOwnerId(userEntity.getId());
        FileSpecification fileSpecification = new FileSpecification(searchCriteria);
        Page<FileEntity> files = this.fileRepository.findAll(fileSpecification, pageable);
        List<FileInfoDto> fileInfoDtos = this.fileMapper.fileEntitiesToFileInfoDtos(files.getContent());
        return new PageImpl<>(fileInfoDtos, pageable, files.getTotalElements());
    }

    @Override
    public byte[] getFile(Long id) {
        UserEntity userEntity = this.userService.getCurrentUser();
        FileEntity file = this.fileRepository.findByIdAndOwner(id, userEntity)
                .orElseThrow(() ->new FileDoesntExistException("File doesn't exist"));
        byte[] fileBytes = fileStorageService.get(file.getPath()+file.getName());
        return fileBytes;
    }

    @Override
    public FileInfoDto uploadFile(MultipartFile file) {

        UserEntity userEntity = this.userService.getCurrentUser();
        String filenameWithExtension = file.getOriginalFilename();
        String filename = Files.getNameWithoutExtension(filenameWithExtension);
        String extension = this.extensionExtractor.extract(filenameWithExtension);
        long size = file.getSize();

        ExtensionEntity extensionEntity = this.extensionExtensionValidator.validate(extension);

        FileEntity fileEntity = FileEntity.builder()
                .createdBy(userEntity.getEmail())
                .creationDate(LocalDateTime.now())
                .extension(extensionEntity)
                .name(filename)
                .owner(userEntity)
                .size(size)
                .build();

        FileStorageResponse response = fileStorageService.store(fileEntity, file);
        fileEntity.setPath(response.getPath());
        fileEntity.setUrl(response.getUrl());

        FileEntity savedFile = fileRepository.save(fileEntity);
        return this.fileMapper.fileEntityToFileInfoDto(savedFile);
    }

    @Override
    public void deleteFile(Long id) {
        FileEntity file = fileRepository.findById(id)
                .orElseThrow(()-> new FileDoesntExistException("File does not exist"));
        this.fileStorageService.delete(file.getPath());
        this.fileRepository.delete(file);
    }
}
