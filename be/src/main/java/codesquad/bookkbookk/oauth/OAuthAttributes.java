package codesquad.bookkbookk.oauth;

import java.util.Map;

import codesquad.bookkbookk.oauth.data.dto.OAuthUserProfile;
import lombok.Getter;

@Getter
public enum OAuthAttributes {
	GITHUB("google") {
		@Override
		public OAuthUserProfile of(Map<String, Object> attributes) {
			return OAuthUserProfile.builder()
					.email((String) attributes.get("email"))
					.name((String) attributes.get("name"))
					.imageUrl((String) attributes.get("picture"))
					.build();
		}
	};

	public final String providerName;

	OAuthAttributes(String providerName) {
		this.providerName = providerName;
	}

	public abstract OAuthUserProfile of(Map<String, Object> attributes);

}
