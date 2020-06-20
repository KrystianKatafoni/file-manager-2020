package com.katafoni.filemanager.common.security.token.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshToken {
    private String refreshToken;
}
