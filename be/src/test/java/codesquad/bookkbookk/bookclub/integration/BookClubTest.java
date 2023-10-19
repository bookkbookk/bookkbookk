package codesquad.bookkbookk.bookclub.integration;

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
import codesquad.bookkbookk.bookclub.data.dto.CreateBookClubResponse;
import codesquad.bookkbookk.bookclub.data.dto.ReadBookClubResponse;
import codesquad.bookkbookk.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.bookclub.data.entity.MemberBookClub;
import codesquad.bookkbookk.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.bookclub.repository.MemberBookClubRepository;
import codesquad.bookkbookk.jwt.JwtProvider;
import codesquad.bookkbookk.member.data.entity.Member;
import codesquad.bookkbookk.member.repository.MemberRepository;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class BookClubTest extends IntegrationTest {

    @Autowired
    private BookClubRepository bookClubRepository;

    @Autowired
    private MemberBookClubRepository memberBookClubRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("북클럽을 생성한다.")
    void createBookClub() throws IOException {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                    .multiPart("profileImage", File.createTempFile("create", "jpeg"), MediaType.IMAGE_JPEG_VALUE)
                    .multiPart("name", "name")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                    .post("/api/book-clubs")
                .then().log().all()
                    .extract();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getObject("", CreateBookClubResponse.class)
                    .getBookClubId()).isEqualTo(member.getId());
        });
    }

    @Test
    @DisplayName("현재 로그인 한 유저의 북클럽을 조회한다.")
    void readBookClubs() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        MemberBookClub memberBookClub = new MemberBookClub(member, bookClub);
        memberBookClubRepository.save(memberBookClub);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .   header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                    .get("/api/book-clubs")
                .then().log().all()
                    .extract();

        // then
        ReadBookClubResponse result = response.jsonPath().getList("", ReadBookClubResponse.class).get(0);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(result.getName()).isEqualTo(bookClub.getName());
            softAssertions.assertThat(result.getCreatorId()).isEqualTo(member.getId());
        });
    }

}
