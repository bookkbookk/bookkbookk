package codesquad.bookkbookk.member.integration;

import java.io.File;
import java.io.IOException;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.common.error.exception.ApiException;
import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.domain.member.data.dto.MemberResponse;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
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

    @Test
    @DisplayName("저장되어 있지 않은 멤버를 조회하면 예외를 던진다.")
    void readUnsavedMember() {
        //given
        String accessToken = jwtProvider.createAccessToken(1L);
        MemberNotFoundException exception = new MemberNotFoundException();

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
            softAssertions.assertThat(response.statusCode()).isEqualTo(exception.getCode());
            softAssertions.assertThat(response.jsonPath().getObject("", ApiException.class).getMessage())
                    .isEqualTo(exception.getMessage());
        });

    }

    //프로필 이미지 수정 테스트 미구현
    @Test
    @DisplayName("멤버의 프로필을 성공적으로 수정한다.")
    void updateUserProfile() throws IOException {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .multiPart("profileImage", File.createTempFile("create", "jpeg")
                            , MediaType.IMAGE_JPEG_VALUE)
                    .multiPart("nickname", "New nickname")
                .when()
                    .patch("/api/members/profile")
                .then().log().all()
                    .extract();

        //then
        Member result = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(result.getNickname()).isEqualTo("New nickname");
        });

    }

}
