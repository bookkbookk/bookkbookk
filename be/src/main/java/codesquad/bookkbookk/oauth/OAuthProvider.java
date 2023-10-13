package codesquad.bookkbookk.oauth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "oauth2")
public class OAuthProvider {

	private final Map<String, Property> properties = new HashMap<>();

	@Getter
	@ConstructorBinding
	public static class Property {

		private final String clientId;
		private final String clientSecret;
		private final String redirectUri;
		private final String tokenRequestUri;
		private final String userInfoRequestUri;

		public Property(String clientId, String clientSecret, String redirectUri, String tokenRequestUri, String userInfoRequestUri) {
			this.clientId = clientId;
			this.clientSecret = clientSecret;
			this.redirectUri = redirectUri;
			this.tokenRequestUri = tokenRequestUri;
			this.userInfoRequestUri = userInfoRequestUri;
		}

	}

}
