package codesquad.bookkbookk.oauth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.oauth.OAuthDetail;
import codesquad.bookkbookk.oauth.data.dto.OAuthTokenResponse;
import codesquad.bookkbookk.oauth.data.dto.OAuthUserProfile;
import codesquad.bookkbookk.oauth.service.OAuthService;
import codesquad.bookkbookk.oauth.util.OAuthDetailsProvider;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OAuthController {

	private final OAuthDetailsProvider oAuthDetailsProvider;
	private final OAuthService oAuthService;

	@GetMapping("/auth/login/{provider}")
	public ResponseEntity<OAuthUserProfile> login(@RequestParam("code") String code,
												  @PathVariable String provider) {
		OAuthDetail oAuthDetail = oAuthDetailsProvider.findByProviderName(provider);

		OAuthTokenResponse token = oAuthService.requestToken(oAuthDetail, code);
		Map<String, Object> userInfo = oAuthService.requestOauthUserInfo(oAuthDetail, token);
		OAuthUserProfile oAuthUserProfile = OAuthUserProfile.of(provider, userInfo);

		return ResponseEntity.ok()
				.body(oAuthUserProfile);

	}

}
