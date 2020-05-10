package com.katafoni.filemanager.security.token.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshToken {
    private String refreshToken;
}
