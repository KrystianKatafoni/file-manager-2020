package com.katafoni.filemanager.security.token.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface TokenFactory {
    AccessToken createAccessToken(String subject, Collection<? extends GrantedAuthority> authorities);
    RefreshToken createRefreshToken(String subject, Collection<? extends GrantedAuthority> authorities);
}
