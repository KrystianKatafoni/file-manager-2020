package com.katafoni.filemanager.file;

import com.katafoni.filemanager.file.search.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Page<FileInfoDto> getFilesInfo(SearchCriteria searchCriteria, Pageable pageable);
    byte[] getFile(Long id);
    void uploadFile(MultipartFile file);
    void deleteFile(Long id);
}
