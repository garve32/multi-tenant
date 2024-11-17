package com.example.multitenant.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private final String KEY = "5kZHdTLiJrNc6UnoRALCax20FRk9kqtDzDkL2NDgeC0=";
    private SecretKey secretKey;

    @PostConstruct
    protected void init() {

        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));
    }

    public String generateToken(String tenantId, String userId) {
        return Jwts.builder()
                .claims(Map.of("tenantId", tenantId, "userId", userId))
                .subject(userId)
                .issuedAt(Date.from(Instant.now()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(secretKey)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    public String parseTenantId(String token) {
        return (String) parseToken(token).get("tenantId");
    }

    public String parseUserId(String token) {
        return parseToken(token).getSubject();
    }
}
