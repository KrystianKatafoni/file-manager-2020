package com.katafoni.filemanager.file;

import com.katafoni.filemanager.file.extension.AcceptableExtension;
import com.katafoni.filemanager.security.user.User;
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
    private AcceptableExtension extension;

    private String url;

    @NotNull
    private String path;

    @ManyToOne
    @NotNull
    private User owner;

    private String createdBy;

    @NotNull
    private LocalDateTime creationDate;
}
