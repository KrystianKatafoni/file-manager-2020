package com.katafoni.filemanager.common.security.token.parse;

import com.katafoni.filemanager.common.security.JwtProperties;
import com.katafoni.filemanager.common.security.token.model.AccessToken;
import com.katafoni.filemanager.common.security.token.model.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TokenCredentialsParser implements TokenParser{

    private final JwtProperties jwtProperties;

    public TokenCredentialsParser(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public ParsedCredentials parse(AccessToken accessToken) throws JwtException {
        String secretKey = jwtProperties.getAccessTokenSecretKey();
        return parseToken(secretKey, accessToken.getAccessToken());
    }
    @Override
    public ParsedCredentials parse(RefreshToken refreshToken) throws JwtException {
        String secretKey = jwtProperties.getRefreshTokenSecretKey();
        return parseToken(secretKey, refreshToken.getRefreshToken());
    }

    private ParsedCredentials parseToken(String secretKey, String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        String username = body.getSubject();
        var authorities = (List<Map<String, String>>) body.get("authorities");
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                .collect(Collectors.toSet());
        return new ParsedCredentials(username, simpleGrantedAuthorities);
    }
}
