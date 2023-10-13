package codesquad.bookkbookk.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
public class JwtProperties {

	private final String secretKey;
	private final Long accessTokenExpiration;
	private final Long refreshTokenExpiration;

	public JwtProperties(String secretKey, Long accessTokenExpiration, Long refreshTokenExpiration) {
		this.secretKey = secretKey;
		this.accessTokenExpiration = accessTokenExpiration;
		this.refreshTokenExpiration = refreshTokenExpiration;
	}

}
