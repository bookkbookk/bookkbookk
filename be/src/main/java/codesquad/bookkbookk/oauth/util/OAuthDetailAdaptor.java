package codesquad.bookkbookk.oauth.util;

import java.util.Map;
import java.util.stream.Collectors;

import codesquad.bookkbookk.oauth.OAuthDetail;
import codesquad.bookkbookk.oauth.OAuthProperties;

public class OAuthDetailAdaptor {

	public static Map<String, OAuthDetail> getOauthDetails(OAuthProperties properties) {
		return properties.getClient().entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						entry -> new OAuthDetail(
								entry.getValue(),
								properties.getProvider().get(entry.getKey())
						)
				));
	}

}
