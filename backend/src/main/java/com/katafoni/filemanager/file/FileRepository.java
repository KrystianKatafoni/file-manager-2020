package com.katafoni.filemanager.file;

import com.katafoni.filemanager.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface FileRepository extends JpaRepository<FileEntity, Long>, JpaSpecificationExecutor<FileEntity> {
    Optional<FileEntity> findByIdAndOwner(Long id, User owner);
}
