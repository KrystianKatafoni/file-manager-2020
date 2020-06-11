package com.katafoni.filemanager.storage.aws;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {
    private String keyId;
    private String accessKey;
    private String region;
    private String bucketName;
}
