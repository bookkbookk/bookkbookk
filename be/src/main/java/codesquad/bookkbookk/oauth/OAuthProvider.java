package codesquad.bookkbookk.oauth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Component
@ConfigurationProperties(prefix = "oauth2")
public class OAuthProvider {

	private final Map<String, Property> properties = new HashMap<>();

	@Getter
	@AllArgsConstructor
	public static class Property {

		private final String clientId;
		private final String clientSecret;
		private final String redirectUri;
		private final String tokenRequestUri;
		private final String userInfoRequestUri;

	}

}
