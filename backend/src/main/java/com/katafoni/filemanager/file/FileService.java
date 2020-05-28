package com.katafoni.filemanager.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FileService {
    Page<FileDto> getDocuments(Pageable pageable);
}
