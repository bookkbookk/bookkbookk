package codesquad.bookkbookk.member.integration;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

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
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.data.entity.MemberBook;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.book.repository.MemberBookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
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
    private BookClubRepository bookClubRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MemberBookRepository memberBookRepository;
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
        MemberResponse result = response.jsonPath().getObject("", MemberResponse.class);
        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(result.getId()).isEqualTo(member.getId());
            softAssertions.assertThat(result.getNickname()).isEqualTo(member.getNickname());
            softAssertions.assertThat(result.getEmail()).isEqualTo(member.getEmail());
            softAssertions.assertThat(result.getProfileImgUrl()).isEqualTo(member.getProfileImgUrl());
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

    @Test
    @DisplayName("요청한 멤버가 참여한 책들을 페이지로 나눠서 보내준다.")
    void readBooks() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book1 = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book1);
        Book book2 = TestDataFactory.createBook2(bookClub);
        bookRepository.save(book2);

        MemberBook memberBook1 = new MemberBook(member, book1);
        memberBookRepository.save(memberBook1);

        MemberBook memberBook2 = new MemberBook(member, book2);
        memberBookRepository.save(memberBook2);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("page", 0)
                .queryParam("size", 1)
                .when()
                .get("/api/members/books")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(((LinkedHashMap) response.jsonPath().getList("books").get(0)).get("title"))
                    .isEqualTo("신데렐라");
        });
    }


}
