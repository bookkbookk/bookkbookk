package codesquad.bookkbookk.oauth.util;

import java.util.HashMap;
import java.util.Map;

import codesquad.bookkbookk.oauth.OAuthDetail;

public class OAuthDetailsProvider {

	private final Map<String, OAuthDetail> providers;

	public OAuthDetailsProvider(Map<String, OAuthDetail> oAuthDetails) {
		this.providers = new HashMap<>(oAuthDetails);
	}

	public OAuthDetail findByProviderName(String name) {
		return providers.get(name);
	}

}

