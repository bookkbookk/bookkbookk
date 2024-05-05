package codesquad.bookkbookk.auth.integration;

import static java.lang.Thread.*;

import java.util.UUID;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.common.error.exception.RefreshTokenNotSavedException;
import codesquad.bookkbookk.common.jwt.JwtProperties;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.common.redis.RedisService;
import codesquad.bookkbookk.domain.auth.service.AuthenticationService;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AuthTest extends IntegrationTest {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisService redisService;

    @Test
    @DisplayName("refreshToken으로 accessToken 재발급을 한다.")
    void reissueAccessToken() throws InterruptedException {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        String uuid = UUID.randomUUID().toString();
        String accessToken = jwtProvider.createAccessToken(member.getId());
        String refreshToken = jwtProvider.createRefreshToken(uuid);
        redisService.saveRefreshTokenUuid(uuid, member.getId());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .domain("localhost")
                .path("/")
                .maxAge(jwtProperties.getRefreshTokenExpiration() / 1000)
                .build();
        sleep(jwtProperties.getRefreshTokenExpiration() / 2);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.COOKIE, cookie.toString())
                .when()
                .post("/api/auth/reissue")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertions.assertThat(response.jsonPath().getString("accessToken"))
                    .isNotBlank();
            assertions.assertThat(response.jsonPath().getString("accessToken"))
                    .isNotEqualTo(accessToken);
        });
    }

    @Test
    @DisplayName("refresh token이 redis에 없으면 accessToken 재발급이 실패한다.")
    void reissueAccessTokenWithExpiredRefreshToken() throws InterruptedException {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        String refreshToken = jwtProvider.createRefreshToken("uuid");

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .domain("localhost")
                .path("/")
                .maxAge(jwtProperties.getRefreshTokenExpiration() / 1000)
                .build();

        RefreshTokenNotSavedException exception = new RefreshTokenNotSavedException();

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.COOKIE, cookie.toString())
                .when()
                .post("/api/auth/reissue")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            assertions.assertThat(response.jsonPath().getString("message")).isEqualTo(exception.getMessage());
        });
    }

    @Test
    @DisplayName("로그아웃을 성공한다.")
    void logout() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        String uuid = UUID.randomUUID().toString();
        String refreshToken = jwtProvider.createRefreshToken(uuid);
        redisService.saveRefreshTokenUuid(uuid, member.getId());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .domain("localhost")
                .path("/")
                .maxAge(jwtProperties.getRefreshTokenExpiration() / 1000)
                .build();

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.COOKIE, cookie.toString())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .post("/api/auth/logout")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertions.assertThatThrownBy(() -> authenticationService.reissueAccessToken(uuid))
                    .isInstanceOf(RefreshTokenNotSavedException.class);
            assertions.assertThat(redisService.getMemberIdByUuid(uuid)).isNull();
            assertions.assertThat(redisService.isAccessTokenPresent(accessToken)).isTrue();
        });
    }

}
