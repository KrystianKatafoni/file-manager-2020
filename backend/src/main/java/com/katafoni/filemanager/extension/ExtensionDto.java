package com.katafoni.filemanager.extension;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtensionDto {

    private Long id;
    private String extension;
    private boolean enabled;
}
