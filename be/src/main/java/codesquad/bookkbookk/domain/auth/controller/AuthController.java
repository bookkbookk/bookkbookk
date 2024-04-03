package codesquad.bookkbookk.domain.auth.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.common.jwt.JwtProperties;
import codesquad.bookkbookk.common.resolver.AccessToken;
import codesquad.bookkbookk.common.resolver.RefreshTokenUuid;
import codesquad.bookkbookk.domain.auth.data.dto.AuthCode;
import codesquad.bookkbookk.domain.auth.data.dto.LoginResponse;
import codesquad.bookkbookk.domain.auth.data.dto.ReissueResponse;
import codesquad.bookkbookk.domain.auth.data.property.CookieProperty;
import codesquad.bookkbookk.domain.auth.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtProperties jwtProperties;
    private final CookieProperty cookieProperty;

    @PostMapping("/login/{providerName}")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthCode authCode, @PathVariable String providerName) {
        LoginResponse loginResponse = authenticationService.login(authCode, providerName);
        ResponseCookie refreshToken = ResponseCookie.from("refreshToken", loginResponse.getRefreshToken())
                .httpOnly(true)
                .secure(cookieProperty.isSecure())
                .maxAge(jwtProperties.getRefreshTokenExpiration() / 1000)
                .domain(cookieProperty.getDomain())
                .path("/api")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshToken.toString())
                .body(loginResponse);
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponse> reissueAccessToken(@RefreshTokenUuid String uuid) {
        ReissueResponse response = authenticationService.reissueAccessToken(uuid);

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AccessToken String accessToken, @RefreshTokenUuid String refreshToken) {
        authenticationService.logout(accessToken, refreshToken);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(cookieProperty.isSecure())
                .maxAge(0)
                .domain(cookieProperty.getDomain())
                .path("/api")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

}
