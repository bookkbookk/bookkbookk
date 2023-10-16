package codesquad.bookkbookk.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.jwt.JwtProvider;
import codesquad.bookkbookk.auth.data.dto.OAuthCode;
import codesquad.bookkbookk.auth.data.dto.LoginResponse;
import codesquad.bookkbookk.auth.service.OAuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final OAuthService oAuthService;
    private final JwtProvider jwtFactory;

    //기능 동작 테스트용 (@RequestParam을 @RequestBody로 변경할 것)
    @PostMapping("/login/{providerName}")
    public ResponseEntity<LoginResponse> login(@RequestBody OAuthCode oAuthCode,
                                               @PathVariable String providerName) {
        LoginResponse loginResponse = oAuthService.login(oAuthCode, providerName);

        return ResponseEntity.ok()
                .body(loginResponse);
    }

}
