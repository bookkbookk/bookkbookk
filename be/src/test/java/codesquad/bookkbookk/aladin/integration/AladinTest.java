package codesquad.bookkbookk.aladin.integration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.domain.book.data.dto.ReadAladinBooksResponse;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AladinTest extends IntegrationTest {

    @Autowired
    private JwtProvider jwtProvider;

    @DisplayName("성공적으로 알라딘 책을 검색한다")
    @Test
    void searchTest() {
        String accessToken = jwtProvider.createAccessToken(1L);
        String searchTerm = "베베북 : 아가랑 삐악삐악";

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .   header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("search", searchTerm)
                .when()
                .get("/api/aladin/books")
                .then().log().all()
                .extract();

       ReadAladinBooksResponse result = response.jsonPath().getList("", ReadAladinBooksResponse.class).get(0);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(result.getTitle()).isEqualTo("베베북 : 아가랑 삐악삐악");
            softAssertions.assertThat(result.getIsbn13()).isEqualTo("9788915002388");
        });

    }

}
