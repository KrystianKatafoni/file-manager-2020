package com.katafoni.filemanager.security.token.parse;

import com.katafoni.filemanager.security.token.model.AccessToken;
import com.katafoni.filemanager.security.token.model.RefreshToken;
import io.jsonwebtoken.JwtException;

public interface TokenParser {
    ParsedCredentials parse(RefreshToken refreshToken) throws JwtException;
    ParsedCredentials parse(AccessToken accessToken) throws JwtException;
}
