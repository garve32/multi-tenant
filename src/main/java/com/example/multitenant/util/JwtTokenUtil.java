package com.example.multitenant.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

//    private final AuthenticationManager authenticationManager;

    @Value("${jwt.secret}")
    private String secret;
    private SecretKey secretKey;

    @Value("${jwt.access-token-expiry}")
    private long accessTokenExpiry;

    @Value("${jwt.refresh-token-expiry}")
    private long refreshTokenExpiry;

    @PostConstruct
    protected void init() {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String createAccessTokenTemp() {
        String currentTenant = "517";
        String userId = "1";

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expiresAt = now.plusSeconds(accessTokenExpiry);

        return Jwts.builder()
                .signWith(secretKey)
                // todo : issuer 지정 필요
                .issuer("issuer")
                .subject(userId)
                .issuedAt(Date.from(now.toInstant()))
                .expiration(Date.from(expiresAt.toInstant()))
                .claims(Map.of("tenantId", currentTenant, "userId", userId))
                .compact();
    }

    public String createAccessToken(String userId, String tenantId) {
//        String currentTenant = TenantContext.getCurrentTenant();
//        String userId = userDetails.getUsername();

        return Jwts.builder()
                .signWith(secretKey)
                // todo : issuer 지정 필요
                .issuer("issuer")
                .subject(userId)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(accessTokenExpiry)))
                .claims(Map.of("tenantId", tenantId, "userId", userId))
                .compact();
    }

    public String createRefreshToken() {
        return Jwts.builder()
                .signWith(secretKey)
                // todo : issuer 지정 필요
                .issuer("issuer")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(refreshTokenExpiry)))
                .compact();
    }

    private Claims parseToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    public String parseTenantId(String token) {
        return (String) parseToken(token).get("tenantId");
    }

    public String parseUserId(String token) {
        return parseToken(token).getSubject();
    }

    /**
     *
     * @param token JWT
     * @return
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     *
     * @param token JWT
     * @return boolean token 검증 결과
     */
    public Boolean validateToken(final String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException e) {
            log.warn("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
