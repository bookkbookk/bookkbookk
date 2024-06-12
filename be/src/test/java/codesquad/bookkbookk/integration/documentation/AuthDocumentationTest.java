package codesquad.bookkbookk.integration.documentation;

import static java.lang.Thread.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;

import java.util.UUID;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.restdocs.payload.JsonFieldType;

import codesquad.bookkbookk.common.error.exception.RefreshTokenNotSavedException;
import codesquad.bookkbookk.common.jwt.JwtProperties;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.common.redis.RedisService;
import codesquad.bookkbookk.domain.auth.service.AuthenticationService;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.util.IntegrationTest;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AuthDocumentationTest extends IntegrationTest {

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
    @DisplayName("리프레시 토큰으로 액세스 토큰을 재발급한다.")
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
                .given(this.spec).log().all()
                .header(HttpHeaders.COOKIE, cookie.toString())
                .filter(document("{class-name}/{method-name}",
                        responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("재발급한 Access Token")
                        )))
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
    @DisplayName("멤버가 로그아웃한다.")
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
                .given(this.spec).log().all()
                .header(HttpHeaders.COOKIE, cookie.toString())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .filter(document("{class-name}/{method-name}",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Access Token 헤더"),
                                headerWithName(HttpHeaders.COOKIE).description("Refresh Token 헤더")
                        )))
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
