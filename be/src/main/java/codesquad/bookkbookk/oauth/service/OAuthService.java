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

import codesquad.bookkbookk.oauth.OAuthProvider;
import codesquad.bookkbookk.oauth.data.dto.OAuthTokenResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthService {

    public String requestOAuthToken(OAuthProvider.Property oAuthProperty, String authCode) {
        return WebClient.create()
                .post()
                .uri(oAuthProperty.getTokenRequestUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(createTokenRequestBody(oAuthProperty, authCode))
                .retrieve()
                .bodyToMono(OAuthTokenResponse.class)
                .map(OAuthTokenResponse::getAccessToken)
                .block();
    }

    private MultiValueMap<String, String> createTokenRequestBody(OAuthProvider.Property property, String authCode) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("client_id", property.getClientId());
        formData.add("client_secret", property.getClientSecret());
        formData.add("redirect_uri", property.getRedirectUri());
        formData.add("code", authCode);
        formData.add("grant_type", "authorization_code");
        return formData;
    }

    public Map<String, Object> requestOAuthUserInfo(OAuthProvider.Property oAuthDetail,
                                                    String tokenResponse) {
        return WebClient.create()
                .get()
                .uri(oAuthDetail.getUserInfoRequestUri())
                .headers(header -> header.setBearerAuth(tokenResponse))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }

}
