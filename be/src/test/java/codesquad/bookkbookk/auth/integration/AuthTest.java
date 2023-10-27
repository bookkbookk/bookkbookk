package codesquad.bookkbookk.auth.integration;

import static java.lang.Thread.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.common.error.exception.RefreshTokenNotSavedException;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.domain.auth.data.entity.MemberRefreshToken;
import codesquad.bookkbookk.domain.auth.repository.MemberRefreshTokenRepository;
import codesquad.bookkbookk.domain.auth.service.OAuthService;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AuthTest extends IntegrationTest {

    @Autowired
    private OAuthService oAuthService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberRefreshTokenRepository memberRefreshTokenRepository;
    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("refreshToken으로 accessToken 재발급을 한다.")
    void reissueAccessToken() throws InterruptedException {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());
        String refreshToken = jwtProvider.createRefreshToken();
        MemberRefreshToken memberRefreshToken = new MemberRefreshToken(member.getId(), refreshToken);
        memberRefreshTokenRepository.save(memberRefreshToken);
        sleep(3000);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken)
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
    @DisplayName("로그아웃을 성공한다.")
    void logout() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());
        String refreshToken = jwtProvider.createRefreshToken();
        MemberRefreshToken memberRefreshToken = new MemberRefreshToken(member.getId(), refreshToken);
        memberRefreshTokenRepository.save(memberRefreshToken);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                    .post("/api/auth/logout")
                .then().log().all()
                    .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertions.assertThatThrownBy(
                    () -> oAuthService.reissueAccessToken(refreshToken)
            ).isInstanceOf(RefreshTokenNotSavedException.class);
        });
    }

}