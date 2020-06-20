package com.katafoni.filemanager.file;

import com.katafoni.filemanager.extension.ExtensionEntity;
import com.katafoni.filemanager.common.security.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="file")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private long size;

    @NotNull
    @ManyToOne
    private ExtensionEntity extension;

    private String url;

    @NotNull
    private String path;

    @ManyToOne
    @NotNull
    private UserEntity owner;

    private String createdBy;

    @NotNull
    private LocalDateTime creationDate;
}
