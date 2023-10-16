package codesquad.bookkbookk.oauth.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import codesquad.bookkbookk.jwt.Jwt;
import codesquad.bookkbookk.jwt.JwtProvider;
import codesquad.bookkbookk.member.data.entity.Member;
import codesquad.bookkbookk.member.repository.MemberRepository;
import codesquad.bookkbookk.oauth.data.provider.OAuthProvider;
import codesquad.bookkbookk.oauth.data.dto.LoginResponse;
import codesquad.bookkbookk.oauth.data.dto.OAuthTokenResponse;
import codesquad.bookkbookk.oauth.data.dto.LoginRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final MemberRepository memberRepository;
    private final OAuthProvider oAuthProvider;
    private final JwtProvider jwtProvider;

    @Transactional
    public LoginResponse login(String authCode, String providerName) {
        OAuthProvider.Property oAuthProperty = oAuthProvider.getProperties().get(providerName);
        String oAuthToken = requestOAuthToken(oAuthProperty, authCode);
        Map<String, Object> oAuthUserInfos = requestOAuthUserInfos(oAuthProperty, oAuthToken);
        LoginRequest loginRequest = LoginRequest.of(providerName, oAuthUserInfos);

        boolean doesMemberExist = memberRepository.existsByEmail(loginRequest.getEmail());
        Member loginMember = getLoginMember(loginRequest, doesMemberExist);
        Jwt jwt = jwtProvider.createJwt(loginMember.getId());

        return LoginResponse.of(jwt, doesMemberExist);
    }

    private Member getLoginMember(LoginRequest loginRequest, boolean doesMemberExist) {
        if (doesMemberExist) {
            return memberRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(RuntimeException::new);
        }
        return memberRepository.save(Member.from(loginRequest));
    }

    private String requestOAuthToken(OAuthProvider.Property oAuthProperty, String authCode) {
        return WebClient.create()
                .post()
                .uri(oAuthProperty.getTokenRequestUrl())
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

    private MultiValueMap<String, String> createTokenRequestBody(OAuthProvider.Property oAuthProperty,
                                                                 String authCode) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("client_id", oAuthProperty.getClientId());
        formData.add("client_secret", oAuthProperty.getClientSecret());
        formData.add("redirect_uri", oAuthProperty.getRedirectUrl());
        formData.add("code", authCode);
        formData.add("grant_type", "authorization_code");
        return formData;
    }

    private Map<String, Object> requestOAuthUserInfos(OAuthProvider.Property oAuthProperty,
                                                    String oAuthToken) {
        return WebClient.create()
                .get()
                .uri(oAuthProperty.getUserInfoRequestUrl())
                .headers(header -> header.setBearerAuth(oAuthToken))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }

}
