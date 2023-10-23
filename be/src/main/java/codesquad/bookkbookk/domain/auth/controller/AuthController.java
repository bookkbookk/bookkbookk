package codesquad.bookkbookk.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.common.resolver.Token;
import codesquad.bookkbookk.domain.auth.data.dto.AuthCode;
import codesquad.bookkbookk.domain.auth.data.dto.LoginResponse;
import codesquad.bookkbookk.domain.auth.data.dto.ReissueResponse;
import codesquad.bookkbookk.domain.auth.service.OAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final OAuthService oAuthService;

    @PostMapping("/login/{providerName}")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthCode authCode,
                                               @PathVariable String providerName) {
        LoginResponse loginResponse = oAuthService.login(authCode, providerName);

        return ResponseEntity.ok()
                .body(loginResponse);
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponse> reissueAccessToken(@Token String refreshToken) {
        ReissueResponse response = oAuthService.reissueAccessToken(refreshToken);

        return ResponseEntity.ok()
                .body(response);
    }

}
