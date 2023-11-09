package codesquad.bookkbookk.gathering.integration;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.common.error.exception.BookNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.gathering.data.dto.CreateGatheringRequest;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class GatheringTest extends IntegrationTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BookClubRepository bookClubRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookClubMemberRepository bookClubMemberRepository;
    @Autowired
    JwtProvider jwtProvider;

    @Test
    @DisplayName("모임을 만는다.")
    void createGathering() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);
        Book book = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book);
        bookClubMemberRepository.save(new BookClubMember(bookClub, member));
        String accessToken = jwtProvider.createAccessToken(member.getId());
        CreateGatheringRequest createGatheringRequest =
                new CreateGatheringRequest(book.getId(), "코드스쿼드", LocalDateTime.of(2023, 10, 20, 12, 30));

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createGatheringRequest)
                .when()
                .post("/api/gatherings")
                .then().log().all()
                .extract();

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("북클럽에 소속되지 않은 멤버가 모임을 만들려하면 예외가 발생한다.")
    void MemberNotInBookClubCreateGathering() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);
        Book book = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book);
        String accessToken = jwtProvider.createAccessToken(member.getId());
        CreateGatheringRequest createGatheringRequest =
                new CreateGatheringRequest(book.getId(), "코드스쿼드", LocalDateTime.of(2023, 10, 20, 12, 30));
        MemberNotInBookClubException exception = new MemberNotInBookClubException();

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createGatheringRequest)
                .when()
                .post("/api/gatherings")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(response.statusCode()).isEqualTo(exception.getCode());
            assertions.assertThat(response.jsonPath().getString("message")).isEqualTo(exception.getMessage());
        });

    }

    @Test
    @DisplayName("모임을 만들 때 책을 조회하지 못하면 예외를 던진다.")
    void createGatheringWithNonSavedBook() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);
        bookClubMemberRepository.save(new BookClubMember(bookClub, member));
        String accessToken = jwtProvider.createAccessToken(member.getId());
        CreateGatheringRequest createGatheringRequest =
                new CreateGatheringRequest(1L, "코드스쿼드", LocalDateTime.of(2023, 10, 20, 12, 30));
        BookNotFoundException exception = new BookNotFoundException();

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createGatheringRequest)
                .when()
                .post("/api/gatherings")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(response.statusCode()).isEqualTo(exception.getCode());
            assertions.assertThat(response.jsonPath().getString("message")).isEqualTo(exception.getMessage());
        });
    }

}
