package codesquad.bookkbookk.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
public class JwtProperties {

    private final String secretKey;
    private final Long accessTokenExpiration;
    private final Long refreshTokenExpiration;

}
