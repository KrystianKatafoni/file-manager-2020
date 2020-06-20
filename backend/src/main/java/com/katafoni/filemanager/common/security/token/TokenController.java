package com.katafoni.filemanager.common.security.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.katafoni.filemanager.common.security.JwtProperties;
import com.katafoni.filemanager.common.security.exception.InvalidTokenException;
import com.katafoni.filemanager.common.security.token.model.AccessToken;
import com.katafoni.filemanager.common.security.token.model.RefreshToken;
import com.katafoni.filemanager.common.security.token.model.TokenFactory;
import com.katafoni.filemanager.common.security.token.parse.ParsedCredentials;
import com.katafoni.filemanager.common.security.token.parse.TokenParser;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;
    private final TokenFactory tokenFactory;
    private final TokenParser tokenParser;

    public TokenController(JwtProperties jwtProperties, ObjectMapper objectMapper, TokenFactory tokenFactory,
                           TokenParser tokenParser) {
        this.jwtProperties = jwtProperties;
        this.objectMapper = objectMapper;
        this.tokenFactory = tokenFactory;
        this.tokenParser = tokenParser;
    }

    @PostMapping("/get-access-token")
    @ResponseStatus(HttpStatus.OK)
    public Map<String,String> refreshAccessToken(@RequestBody String requestBody) throws JsonProcessingException {

        JsonNode jsonNode = objectMapper.readTree(requestBody);
        String refreshToken = jsonNode.get("refresh_token").asText();

        if(Strings.isNullOrEmpty(refreshToken)) {
            throw new InvalidTokenException(refreshToken);
        }

        try{
            ParsedCredentials parsedCredentials = tokenParser.parse(new RefreshToken(refreshToken));
            AccessToken accessToken = tokenFactory.createAccessToken(parsedCredentials.getUsername(),
                    parsedCredentials.getAuthorities());
            Map<String,String> tokenMap = ImmutableMap.<String,String> builder()
                    .put("access_token", accessToken.getAccessToken())
                    .put("refresh_token", refreshToken).build();

            return tokenMap;
        }catch (JwtException ex) {
            throw new InvalidTokenException(refreshToken);
        }
    }

    @PostMapping("get-refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public RefreshToken getRefreshToken(@RequestBody String requestBody) throws JsonProcessingException {

        JsonNode jsonNode = objectMapper.readTree(requestBody);
        String refreshToken = jsonNode.get("refresh_token").asText();

        if(Strings.isNullOrEmpty(refreshToken)) {
            throw new InvalidTokenException(refreshToken);
        }

        try{
            ParsedCredentials parsedCredentials = tokenParser.parse(new RefreshToken(refreshToken));
            RefreshToken retrievedRefreshToken = tokenFactory.createRefreshToken(parsedCredentials.getUsername(),
                    parsedCredentials.getAuthorities());
            return new RefreshToken(jwtProperties.getRefreshTokenType() + " "+ retrievedRefreshToken.getRefreshToken());
        }catch (JwtException ex) {
            throw new InvalidTokenException(refreshToken);
        }
    }
}