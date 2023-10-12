package codesquad.bookkbookk.oauth;

import lombok.Getter;

@Getter
public class OAuthDetail {

	private final String clientId;
	private final String clientSecret;
	private final String redirectUri;
	private final String tokenRequestUri;
	private final String userInfoRequestUri;

	public OAuthDetail(OAuthProperties.Client client, OAuthProperties.Provider provider) {
		this(client.getClientId(), client.getClientSecret(), client.getRedirectUri(),
				provider.getTokenRequestUri(), provider.getUserInfoRequestUri()
		);
	}

	public OAuthDetail(String clientId, String clientSecret, String redirectUri,
					   String tokenUri, String userInfoUri) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUri = redirectUri;
		this.tokenRequestUri = tokenUri;
		this.userInfoRequestUri = userInfoUri;
	}

}

