package com.katafoni.filemanager.file;

import com.katafoni.filemanager.common.security.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface FileRepository extends JpaRepository<FileEntity, Long>, JpaSpecificationExecutor<FileEntity> {
    Optional<FileEntity> findByIdAndOwner(Long id, UserEntity owner);
}
