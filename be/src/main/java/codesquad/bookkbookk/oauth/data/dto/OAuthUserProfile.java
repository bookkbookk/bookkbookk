package codesquad.bookkbookk.oauth.data.dto;

import java.util.Arrays;
import java.util.Map;

import codesquad.bookkbookk.oauth.OAuthAttributes;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthUserProfile {

	private final String email;
	private final String name;
	private final String imageUrl;

	@Builder
	public OAuthUserProfile(String email, String name, String imageUrl) {
		this.email = email;
		this.name = name;
		this.imageUrl = imageUrl;
	}

	public static OAuthUserProfile of(String providerName, Map<String, Object> attributes) {
		return Arrays.stream(OAuthAttributes.values())
				.filter(provider -> providerName.equals(provider.providerName))
				.findFirst()
				.orElseThrow()
				.of(attributes);
	}

}
