package com.katafoni.filemanager.common.security.token;

import lombok.Data;

@Data
public class UsernameAndPasswordAuthenticationRequest {
    private String email;
    private String password;
}

