package com.katafoni.filemanager.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katafoni.filemanager.security.auth.ApplicationUserService;
import com.katafoni.filemanager.security.token.JwtTokenVerifier;
import com.katafoni.filemanager.security.token.JwtUsernameAndPasswordAuthenticationFilter;
import com.katafoni.filemanager.security.token.model.TokenFactory;
import com.katafoni.filemanager.security.token.parse.TokenParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;
    private final TokenFactory tokenFactory;
    private final TokenParser tokenParser;

    public SecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService,
                          JwtProperties jwtProperties, ObjectMapper objectMapper, TokenFactory tokenFactory,
                          TokenParser tokenParser) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.jwtProperties = jwtProperties;
        this.objectMapper = objectMapper;
        this.tokenFactory = tokenFactory;
        this.tokenParser = tokenParser;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/tokens/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), this.jwtProperties,
                        objectMapper, tokenFactory))
                .addFilterAfter(new JwtTokenVerifier(this.jwtProperties, tokenParser),
                        JwtUsernameAndPasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }


    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Don't do this in production, use a proper list  of allowed origins
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "observe", "Authorization"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        config.setExposedHeaders(Arrays.asList("Authorization"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
