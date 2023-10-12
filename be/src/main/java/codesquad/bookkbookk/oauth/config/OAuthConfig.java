package codesquad.bookkbookk.oauth.config;

import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import codesquad.bookkbookk.oauth.OAuthDetail;
import codesquad.bookkbookk.oauth.OAuthProperties;
import codesquad.bookkbookk.oauth.util.OAuthDetailAdaptor;
import codesquad.bookkbookk.oauth.util.OAuthDetailsProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableConfigurationProperties(OAuthProperties.class)
@RequiredArgsConstructor
public class OAuthConfig {

	private final OAuthProperties oAuthProperties;

	@Bean
	public OAuthDetailsProvider oAuthDetailsProvide() {
		Map<String, OAuthDetail> oAuthDetails =
				OAuthDetailAdaptor.getOauthDetails(oAuthProperties);
		return new OAuthDetailsProvider(oAuthDetails);
	}

}
