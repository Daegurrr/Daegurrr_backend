package com.daegurrr.daefree.authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtManager {
    @Value("${jwt.access-token-expiration}")
    private long jwtAccessExpiration;
    @Value("${jwt.refresh-token-expiration}")
    private long jwtRefreshExpiration;
    @Value("${jwt.secret}")
    private String jwtSecret;

    public String issueToken(Long id) {
        long current = System.currentTimeMillis();
        Date accessTokenExpireTime = new Date(current + jwtAccessExpiration);

        String accessToken = generateToken(accessTokenExpireTime, id.toString());

        return accessToken;
    }

    private String generateToken(Date expiration, String subject) {
        Key secretKey = createSignature();

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validateToken(String value) {
        if (value == null || !value.startsWith("Bearer ")) {
            throw new RuntimeException("토큰이 존재하지 않거나, 유효하지 않은 형식입니다.");
        }
        // Bearer 제거
        String token = value.substring(7);
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(createSignature())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("토큰이 만료되었습니다.");
        } catch (SignatureException e) {
            throw new RuntimeException("디지털 서명이 일치하지 않습니다.");
        }
    }

    private Key createSignature() {
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
    }


}
