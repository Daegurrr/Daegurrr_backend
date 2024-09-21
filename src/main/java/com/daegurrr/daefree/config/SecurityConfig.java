package com.daegurrr.daefree.config;

import com.daegurrr.daefree.authentication.JwtAccessDeniedHandler;
import com.daegurrr.daefree.authentication.JwtAuthenticationEntryPoint;
import com.daegurrr.daefree.authentication.JwtFilter;
import com.daegurrr.daefree.authentication.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtManager jwtManager;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private static final String[] AUTHENTICATION_LIST = {
        "api/auth/test"
    };
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
        httpSecurity
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtFilter(jwtManager), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                        .requestMatchers(AUTHENTICATION_LIST).hasRole("USER")
                        .anyRequest().permitAll()
                );

        return httpSecurity.build();
    }
}
