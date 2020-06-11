package com.katafoni.filemanager.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

/**
 * Class which represent storing request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileStorageRequest {
    private InputStream inputStream;
    private long fileSize;
    private String fileName;
}
