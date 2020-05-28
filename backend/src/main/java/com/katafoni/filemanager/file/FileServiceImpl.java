package com.katafoni.filemanager.file;

import com.katafoni.filemanager.security.user.User;
import com.katafoni.filemanager.security.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FileServiceImpl implements FileService{
    FileRepository fileRepository;
    FileMapper fileMapper;
    UserService userService;

    public FileServiceImpl(FileRepository fileRepository, FileMapper fileMapper, UserService userService) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    @Override
    public Page<FileDto> getDocuments(Pageable pageable) {
        User user = this.userService.getCurrentUser();
        Page<FileEntity> files = this.fileRepository.findByOwner(user, pageable);
        List<FileDto> fileDtos = this.fileMapper.fileEntitiesToFileDtos(files.getContent());
        return new PageImpl<>(fileDtos, pageable, files.getTotalElements());
    }
}
