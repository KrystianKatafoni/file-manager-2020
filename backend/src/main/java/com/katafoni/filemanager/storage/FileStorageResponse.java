package com.katafoni.filemanager.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileStorageResponse {
    private String url;
    private String path;
}
