package com.katafoni.filemanager.common.security.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.katafoni.filemanager.common.security.JwtProperties;
import com.katafoni.filemanager.common.security.exception.InvalidCredentialsException;
import com.katafoni.filemanager.common.security.token.model.AccessToken;
import com.katafoni.filemanager.common.security.token.model.RefreshToken;
import com.katafoni.filemanager.common.security.token.model.TokenFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;
    private final TokenFactory tokenFactory;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                      JwtProperties jwtProperties, ObjectMapper objectMapper,
                                                      TokenFactory tokenFactory) {
        this.authenticationManager = authenticationManager;
        this.jwtProperties = jwtProperties;
        this.objectMapper = objectMapper;
        this.tokenFactory = tokenFactory;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest =
                    new ObjectMapper().readValue(request.getInputStream(),
                            UsernameAndPasswordAuthenticationRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail().toLowerCase(),
                    authenticationRequest.getPassword().toLowerCase()
            );
            return authenticationManager.authenticate(authentication);
        } catch (IOException ex) {
            throw new InvalidCredentialsException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String username = authResult.getName();
        AccessToken accessToken = tokenFactory.createAccessToken(username, authResult.getAuthorities());
        RefreshToken refreshToken = tokenFactory.createRefreshToken(username, authResult.getAuthorities());
        Map<String,String> tokenMap = ImmutableMap.<String,String> builder()
                .put("accessToken", jwtProperties.getAccessTokenType()+ " " + accessToken.getAccessToken())
                .put("refreshToken", jwtProperties.getRefreshTokenType()+" "+ refreshToken.getRefreshToken()).build();
        response.addHeader("Authorization", objectMapper.writeValueAsString(tokenMap));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid user authentication");
    }
}
