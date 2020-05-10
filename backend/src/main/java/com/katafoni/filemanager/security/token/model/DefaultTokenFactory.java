package com.katafoni.filemanager.security.token.model;


import com.katafoni.filemanager.security.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Component
public class DefaultTokenFactory implements TokenFactory{

    private final JwtProperties jwtProperties;

    public DefaultTokenFactory(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public AccessToken createAccessToken(String subject, Collection<? extends GrantedAuthority> authorities) {
        return new AccessToken(Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .setSubject(subject)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(getTokenExpirationDate(jwtProperties.getAccessTokenExpirationAfterMinutes()))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getAccessTokenSecretKey().getBytes()))
                .compact());
    }
    @Override
    public RefreshToken createRefreshToken(String subject, Collection<? extends GrantedAuthority> authorities) {
        return new RefreshToken(Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(getTokenExpirationDate(jwtProperties.getRefreshTokenExpirationAfterMinutes()))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getRefreshTokenSecretKey().getBytes()))
                .compact());
    }


    private Date getTokenExpirationDate (String tokenExpirationAfterMinutes) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Date tokenExpirationDate = Date.from(currentDateTime
                .plusMinutes(Long.valueOf(tokenExpirationAfterMinutes))
                .atZone(ZoneId.systemDefault()).toInstant());
        return tokenExpirationDate;
    }
}
