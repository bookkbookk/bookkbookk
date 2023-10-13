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
public class JwtFactory {

	private static final String TOKEN_PAYLOAD_MEMBER_ID = "memberId";

	private final JwtProperties jwtProperties;

	private Key key;

	@PostConstruct
	private void setKey() {
		key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
	}

	public String generateAccessToken(Long memberId) {
		Date expiration = new Date(
				System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration());
		return createToken(memberId, expiration);
	}

	public String generateRefreshToken(Long memberId) {
		Date expiration = new Date(
				System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration());
		return createToken(memberId, expiration);
	}

	private String createToken(Long memberId, Date expiration) {
		return Jwts.builder()
				.expiration(expiration)
				.claim(TOKEN_PAYLOAD_MEMBER_ID, memberId)
				.signWith(key)
				.compact();
	}

}

