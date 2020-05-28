package com.katafoni.filemanager.file;

import com.katafoni.filemanager.security.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Page<FileEntity> findByOwner(User owner, Pageable pageable);
}
