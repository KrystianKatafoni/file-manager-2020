package com.katafoni.filemanager.extension;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExtensionRepository extends JpaRepository<ExtensionEntity, Long> {
    Optional<ExtensionEntity> findByExtension(String extension);
}
