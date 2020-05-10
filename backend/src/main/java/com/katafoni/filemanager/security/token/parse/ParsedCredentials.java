package com.katafoni.filemanager.security.token.parse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@AllArgsConstructor
@Getter
public class ParsedCredentials {
    String username;
    Set<SimpleGrantedAuthority> authorities;
}
