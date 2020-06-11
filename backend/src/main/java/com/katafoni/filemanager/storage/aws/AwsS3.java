package com.katafoni.filemanager.storage.aws;


import com.katafoni.filemanager.storage.FileStorageRequest;

import java.net.URL;

public interface AwsS3 {
    URL storeFile(FileStorageRequest fileStorageRequest);
    void deleteFile(String path);
    byte[] getFile(String path);
}
