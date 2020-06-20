package com.katafoni.filemanager.common.security.token.parse;

import com.katafoni.filemanager.common.security.token.model.AccessToken;
import com.katafoni.filemanager.common.security.token.model.RefreshToken;
import io.jsonwebtoken.JwtException;

public interface TokenParser {
    ParsedCredentials parse(RefreshToken refreshToken) throws JwtException;
    ParsedCredentials parse(AccessToken accessToken) throws JwtException;
}
