package codesquad.bookkbookk.jwt;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;

    private SecretKey key;

    @PostConstruct
    private void setKey() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
    }

    public Jwt createJwt(Long memberId) {
        return Jwt.builder()
                .accessToken(createAccessToken(memberId))
                .refreshToken(createRefreshToken())
                .build();
    }

    public String createAccessToken(Long memberId) {
        Date expiration = new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration());
        Map<String, Object> claims = Map.of("memberId", memberId);

        return createToken(claims, expiration);
    }

    public String createRefreshToken() {
        Date expiration = new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration());
        Map<String, Object> claims = Collections.emptyMap();

        return createToken(claims, expiration);
    }

    public Long extractMemberId(String token) {
        return getPayloads(token).get("memberId", Long.class);
    }

    public Claims getPayloads(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String createToken(Map<String, Object> claims, Date expiration) {
        return Jwts.builder()
                .expiration(expiration)
                .claims(claims)
                .signWith(key)
                .compact();
    }

}

