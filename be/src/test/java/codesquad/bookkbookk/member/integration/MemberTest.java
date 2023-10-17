package codesquad.bookkbookk.member.integration;

import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.jwt.JwtProvider;
import codesquad.bookkbookk.member.data.dto.MemberResponse;
import codesquad.bookkbookk.member.data.entity.Member;
import codesquad.bookkbookk.member.repository.MemberRepository;
import codesquad.bookkbookk.util.TestDataFactory;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MemberTest extends IntegrationTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("멤버를 조회한다.")
    void readMember() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                    .get("/api/members")
                .then().log().all()
                    .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getObject("", MemberResponse.class).getId())
                    .isEqualTo(member.getId());
        });

    }

}