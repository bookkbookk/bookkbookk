package codesquad.bookkbookk.jwt;

import java.security.Key;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;

    private Key key;

    @PostConstruct
    private void setKey() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
    }

    public Jwt createJwt(Long memberId) {
        return Jwt.builder()
                .accessToken(createAccessToken(memberId))
                .refreshToken(createRefreshToken(memberId))
                .build();
    }

    public String createAccessToken(Long memberId) {
        Date expiration = new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration());

        return createToken(memberId, expiration);
    }

    public String createRefreshToken(Long memberId) {
        Date expiration = new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration());

        return createToken(memberId, expiration);
    }

    private String createToken(Long memberId, Date expiration) {
        return Jwts.builder()
                .expiration(expiration)
                .claim("memberId", memberId)
                .signWith(key)
                .compact();
    }

}

