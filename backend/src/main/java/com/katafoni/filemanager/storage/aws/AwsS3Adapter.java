package com.katafoni.filemanager.storage.aws;


import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.katafoni.filemanager.exception.CannotStoreFileException;
import com.katafoni.filemanager.exception.FileToBytesException;
import com.katafoni.filemanager.storage.FileStorageRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

/**
 * Communication adapter with amazon s3 service
 */
@Component
@Slf4j
public class AwsS3Adapter implements AwsS3 {

    private AmazonS3 amazonS3Client;
    private AwsProperties awsProperties;

    public AwsS3Adapter(AmazonS3 amazonS3Client, AwsProperties awsProperties) {
        this.amazonS3Client = amazonS3Client;
        this.awsProperties = awsProperties;
    }

    /**
     * Store file in aws s3 bucket
     *
     * @param fileStorageRequest request for storing file in s3
     * @return url of stored file
     * @throws CannotStoreFileException when AmazonClientException is throwing
     */
    @Override
    public URL storeFile(FileStorageRequest fileStorageRequest) throws CannotStoreFileException {
        String bucketName = awsProperties.getBucketName();
        String fileName = fileStorageRequest.getFileName();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileStorageRequest.getFileSize());
        try {
            amazonS3Client.putObject(bucketName, fileName,
                    fileStorageRequest.getInputStream(), objectMetadata);
        } catch (AmazonClientException ex) {
            log.error("AmazonClientException, cannot store file, reason: " + ex);
            throw new CannotStoreFileException(fileName);
        }
        return amazonS3Client.getUrl(bucketName, fileName);
    }

    @Override
    public void deleteFile(String path) {
        String bucketName = awsProperties.getBucketName();
        try {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withPrefix(path.substring(0,path.length()-1));
            ObjectListing objectListing = amazonS3Client.listObjects(listObjectsRequest);
            while (true) {
                for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                    amazonS3Client.deleteObject(bucketName, objectSummary.getKey());
                }
                if (objectListing.isTruncated()) {
                    objectListing = amazonS3Client.listNextBatchOfObjects(objectListing);
                } else {
                    break;
                }
            }
        } catch (AmazonServiceException ex) {
            log.error("AmazonServiceException, cannot delete file, reason: " + ex);
        } catch (SdkClientException e) {
            log.error("AmazonServiceException, cannot delete file, reason: " + e);
        }

    }

    @Override
    public byte[] getFile(String path) {
        byte[] file;
        String bucketName = awsProperties.getBucketName();
        S3Object s3object = amazonS3Client.getObject(bucketName, path);
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        try {
            file = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new FileToBytesException("File cannot be converted to bytes");
        }
        return file;
    }
}
