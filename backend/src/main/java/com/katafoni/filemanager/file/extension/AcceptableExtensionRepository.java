package com.katafoni.filemanager.file.extension;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcceptableExtensionRepository extends JpaRepository<AcceptableExtension, Long> {
    Optional<AcceptableExtension> findByExtension(String extension);
}
