package codesquad.bookkbookk.filter;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.error.ErrorResponse;
import codesquad.bookkbookk.common.error.exception.jwt.MalformedTokenException;
import codesquad.bookkbookk.common.error.exception.jwt.NoAuthorizationHeaderException;
import codesquad.bookkbookk.common.jwt.JwtProvider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilterTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JwtProvider jwtProvider;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("토큰이 필요한 요청에 토큰을 넣지 않으면 필터가 작동한다.")
    void requestWithOutToken() throws Exception {
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
            assertThat(response.statusCode()).isEqualTo(exception.getCode());
            assertThat(response.jsonPath().getObject("", ErrorResponse.class).getMessage())
                    .isEqualTo(exception.getMessage());
        });

    }

    @Test
    @DisplayName("변형된 토큰이 요청에 포함되면 필터가 작동한다.")
    void requestWithMalformedToken() throws Exception {
        // given
        MalformedTokenException exception = new MalformedTokenException();
        String token = Jwts.builder()
                .expiration(new Date(System.currentTimeMillis() + 30000))
                .signWith(Keys.hmacShaKeyFor("thisiskeyfortestbookkbookk1234512356!!".getBytes()))
                .compact();

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when()
                    .get("api/members")
                .then().log().all()
                    .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertThat(response.statusCode()).isEqualTo(exception.getCode());
            assertThat(response.jsonPath().getObject("", ErrorResponse.class).getMessage())
                    .isEqualTo(exception.getMessage());
        });
    }

}
