package com.katafoni.filemanager.file.extension;

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
@Table(name = "acceptable_extension")
public class AcceptableExtension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String extension;
}
