package codesquad.bookkbookk.common.jwt;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

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

    public String getToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        return authorization.substring("Bearer ".length());
    }

    public Long extractMemberId(String accessToken) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .get("memberId", Long.class);
    }

    public void validateToken(String token) {
        Jwts.parser()
                .verifyWith(key)
                .build()
                .parse(token);
    }

    private String createToken(Map<String, Object> claims, Date expiration) {
        return Jwts.builder()
                .expiration(expiration)
                .claims(claims)
                .signWith(key)
                .compact();
    }

}

