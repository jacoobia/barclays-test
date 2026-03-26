package com.jacobhampton.techtest.auth.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * I would implement a RefreshToken collection & repository so that they can be revoked
 * i.e. for scenarios like a "sign out of all devices" option if an account is compromised*
 * For now, this just creates and validates tokens and won't persist access tokens by default anyway
 */
@Service
public class TokenService {

    @Value("${app.jwt.secret}")
    private String tokenSecret;

    @Value("${app.jwt.accessTokenExpiry}")
    private long accessTokenExpiry;

    @Value("${app.jwt.refreshTokenExpiry}")
    private long refreshTokenExpiry;

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiry))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(String email) {
        Date expiration = new Date(System.currentTimeMillis() + refreshTokenExpiry);
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException exception) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                        .verifyWith(getSigningKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getSubject();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(tokenSecret.getBytes());
    }

}
