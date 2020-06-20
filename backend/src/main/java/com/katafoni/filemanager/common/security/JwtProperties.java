package com.katafoni.filemanager.common.security;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String accessTokenSecretKey;
    private String refreshTokenSecretKey;
    private String accessTokenExpirationAfterMinutes;
    private String refreshTokenExpirationAfterMinutes;
    private String issuer;
    private String accessTokenType;
    private String refreshTokenType;
}
