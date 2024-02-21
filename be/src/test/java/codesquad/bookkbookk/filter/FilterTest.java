package codesquad.bookkbookk.filter;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.test.context.ActiveProfiles;

import codesquad.bookkbookk.common.error.ErrorResponse;
import codesquad.bookkbookk.common.error.exception.auth.MalformedTokenException;
import codesquad.bookkbookk.common.error.exception.auth.NoAuthorizationHeaderException;
import codesquad.bookkbookk.common.error.exception.auth.TokenNotIncludedException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@ActiveProfiles(profiles = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilterTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("access token이 필요한 요청에 토큰을 넣지 않으면 예외가 발생한다.")
    void requestWithOutAccessToken() throws Exception {
        // given
        NoAuthorizationHeaderException exception = new NoAuthorizationHeaderException();

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when()
                    .get("api/members")
                .then().log().all()
                    .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            assertThat(response.jsonPath().getInt("code")).isEqualTo(exception.getCode());
            assertThat(response.jsonPath().getObject("", ErrorResponse.class).getMessage())
                    .isEqualTo(exception.getMessage());
        });

    }

    @Test
    @DisplayName("변형된 access token이 요청에 포함되면 에외가 한다.")
    void requestWithMalformedAccessToken() throws Exception {
        // given
        MalformedTokenException exception = new MalformedTokenException(4011);
        String accessToken = Jwts.builder()
                .expiration(new Date(System.currentTimeMillis() + 30000))
                .signWith(Keys.hmacShaKeyFor("thisiskeyfortestbookkbookk1234512356!!".getBytes()))
                .compact();

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                    .get("api/members")
                .then().log().all()
                    .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            assertThat(response.jsonPath().getInt("code")).isEqualTo(exception.getCode());
            assertThat(response.jsonPath().getObject("", ErrorResponse.class).getMessage())
                    .isEqualTo(exception.getMessage());
        });
    }

    @Test
    @DisplayName("refresh token이 필요한 요청에 토큰을 넣지 않으면 예외가 발생한다.")
    void requestWithMalformedToken() throws Exception {
        // given
        TokenNotIncludedException exception = new TokenNotIncludedException(4012);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when()
                .post("api/auth/reissue")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            assertThat(response.jsonPath().getInt("code")).isEqualTo(exception.getCode());
            assertThat(response.jsonPath().getObject("", ErrorResponse.class).getMessage())
                    .isEqualTo(exception.getMessage());
        });
    }

    @Test
    @DisplayName("변형된 refresh token이 요청에 포함되면 에외가 한다.")
    void requestWithMalformedRefreshToken() throws Exception {
        // given
        MalformedTokenException exception = new MalformedTokenException(4012);
        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor("thisiskeyfortestbookkbookk1234512356!!".getBytes()))
                .compact();
        ResponseCookie refreshToken = ResponseCookie.from("refreshToken", token)
                .httpOnly(true)
                .secure(true)
                .domain("bookkbookk.site")
                .path("/")
                .build();

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.COOKIE, refreshToken.toString())
                .when()
                .post("api/auth/reissue")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            assertThat(response.jsonPath().getInt("code")).isEqualTo(exception.getCode());
            assertThat(response.jsonPath().getObject("", ErrorResponse.class).getMessage())
                    .isEqualTo(exception.getMessage());
        });
    }
}
