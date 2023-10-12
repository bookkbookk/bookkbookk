package codesquad.bookkbookk.oauth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.oauth.OAuthProvider;
import codesquad.bookkbookk.oauth.data.dto.OAuthUserProfile;
import codesquad.bookkbookk.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OAuthController {

	private final OAuthProvider oAuthProvider;
	private final OAuthService oAuthService;

	@GetMapping("/auth/login/{provider}")
	public ResponseEntity<OAuthUserProfile> login(@RequestParam("code") String code,
												  @PathVariable String provider) {
		Map<String, OAuthProvider.Property> properties = oAuthProvider.getProperties();
		OAuthProvider.Property property = properties.get(provider);
		String token = oAuthService.requestOAuthToken(property, code);
		Map<String, Object> userInfo = oAuthService.requestOAuthUserInfo(property, token);
		OAuthUserProfile oAuthUserProfile = OAuthUserProfile.of(provider, userInfo);

		return ResponseEntity.ok()
				.body(oAuthUserProfile);

	}

}
