package com.katafoni.filemanager.common.security.token;


import com.google.common.base.Strings;
import com.katafoni.filemanager.common.security.JwtProperties;
import com.katafoni.filemanager.common.security.token.model.AccessToken;
import com.katafoni.filemanager.common.security.token.parse.ParsedCredentials;
import com.katafoni.filemanager.common.security.token.parse.TokenParser;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final TokenParser tokenParser;

    public JwtTokenVerifier(JwtProperties jwtProperties, TokenParser tokenParser) {
        this.jwtProperties = jwtProperties;
        this.tokenParser = tokenParser;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(Strings.isNullOrEmpty(authorizationHeader) ||
                !authorizationHeader.startsWith(jwtProperties.getAccessTokenType())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(jwtProperties.getAccessTokenType() + " ", "");
        try{
            ParsedCredentials parsedCredentials = tokenParser.parse(new AccessToken(token));
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    parsedCredentials.getUsername(),
                    null,
                    parsedCredentials.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }catch (JwtException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }

    }

}

