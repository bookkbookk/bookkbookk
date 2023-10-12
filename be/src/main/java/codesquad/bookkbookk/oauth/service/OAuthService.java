package codesquad.bookkbookk.oauth.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import codesquad.bookkbookk.oauth.OAuthDetail;
import codesquad.bookkbookk.oauth.data.dto.OAuthTokenResponse;
import codesquad.bookkbookk.oauth.util.OAuthDetailsProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthService {

	private final OAuthDetailsProvider oAuthDetailsProvider;

	public OAuthTokenResponse requestToken(OAuthDetail oAuthDetail, String authCode) {
		return WebClient.create()
				.post()
				.uri(oAuthDetail.getTokenRequestUri())
				.headers(header -> {
					header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
					header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
					header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
				})
				.bodyValue(requestTokenFormData(oAuthDetail, authCode))
				.retrieve()
				.bodyToMono(OAuthTokenResponse.class)
				.block();
	}

	private MultiValueMap<String, String> requestTokenFormData(OAuthDetail provider, String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("client_id", provider.getClientId());
		formData.add("client_secret", provider.getClientSecret());
		formData.add("redirect_uri", provider.getRedirectUri());
		formData.add("code", code);
		formData.add("grant_type", "authorization_code");
		return formData;
	}

	public Map<String, Object> requestOauthUserInfo(OAuthDetail oAuthDetail,
													   OAuthTokenResponse tokenResponse) {
		return WebClient.create()
				.get()
				.uri(oAuthDetail.getUserInfoRequestUri())
				.headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
				})
				.block();
	}

}
