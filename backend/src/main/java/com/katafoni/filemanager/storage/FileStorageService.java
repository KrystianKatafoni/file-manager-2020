package com.katafoni.filemanager.storage;

import com.katafoni.filemanager.file.FileEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    FileStorageResponse store(FileEntity fileEntity, MultipartFile file);
    byte[] get(String path);
    void delete(String path);
}
