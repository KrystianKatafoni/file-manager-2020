package com.katafoni.filemanager.extension;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Extension which is allowed for conversion fe. jpg, png
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "extension")
public class ExtensionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String extension;

    @NotNull
    private boolean enabled;
}
