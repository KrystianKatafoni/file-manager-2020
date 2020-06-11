package com.katafoni.filemanager.storage;


import com.katafoni.filemanager.file.FileDto;
import com.katafoni.filemanager.file.FileEntity;
import com.katafoni.filemanager.storage.aws.AwsS3;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {

    AwsS3 awsS3;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH-mm-ss_dd-MM-yyyy");

    public FileStorageServiceImpl(AwsS3 awsS3) {
        this.awsS3 = awsS3;
    }

    /**
     * Store converted document and get document object
     *
     * @param file
     * @return document object after storing in awsS3
     */
    @Override
    public FileStorageResponse store(FileEntity fileEntity, MultipartFile file) {
        String urlAsString = "";
        String path = "";
        try {
            LocalDateTime creationTime = LocalDateTime.now();
            String creationTimeAsString = creationTime.format(FORMATTER);
            path = preparePath(fileEntity.getName(), creationTimeAsString);
            String fileName = fileEntity.getName();
            InputStream inputStream = file.getInputStream();
            long fileSize = file.getSize();
            String pathWithFile = path + fileName;
            FileStorageRequest fileStoringRequest = new FileStorageRequest(inputStream, fileSize, pathWithFile);
            URL url = awsS3.storeFile(fileStoringRequest);
            urlAsString = String.valueOf(url);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return new FileStorageResponse(urlAsString, path);
    }


    @Override
    public void delete(String path) {
        awsS3.deleteFile(path);
    }

    @Override
    public byte[] get(String path) {
        return this.awsS3.getFile(path);
    }

    private String preparePath(String fileName, String creationTime) {
        return fileName + "_" + creationTime + "/";
    }
}
