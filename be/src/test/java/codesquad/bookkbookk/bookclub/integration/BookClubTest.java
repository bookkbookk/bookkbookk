package codesquad.bookkbookk.bookclub.integration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.common.error.exception.MemberJoinedBookClubException;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateInvitationUrlRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.ReadBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClubInvitationCode;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClubMember;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubInvitationCodeRepository;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class BookClubTest extends IntegrationTest {

    @Autowired
    private BookClubRepository bookClubRepository;

    @Autowired
    private BookClubMemberRepository bookClubMemberRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookClubInvitationCodeRepository bookClubInvitationCodeRepository;

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
            softAssertions.assertThat(response.jsonPath().getLong("bookClubId")).isEqualTo(member.getId());
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

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

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

    @Test
    @DisplayName("북클럽의 책들을 슬라이스 나눠서 보내준다.")
    void readBookClubBooks() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book1 = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book1);
        Book book2 = TestDataFactory.createBook2(bookClub);
        bookRepository.save(book2);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("cursor", 0)
                .queryParam("size", 1)
                .when()
                .get("/api/book-clubs/" + bookClub.getId() + "/books")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getBoolean("hasNext")).isTrue();
            softAssertions.assertThat(response.jsonPath().getString("books[0].author"))
                    .isEqualTo(book1.getAuthor());
        });
    }

    @Test
    @DisplayName("북클럽의 책들의 마지막 slice를 가져온다.")
    void readBookClubBookLastSlice() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book1 = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book1);
        Book book2 = TestDataFactory.createBook2(bookClub);
        bookRepository.save(book2);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("cursor", 1)
                .queryParam("size", 1)
                .when()
                .get("/api/book-clubs/" + bookClub.getId() + "/books")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getBoolean("hasNext")).isFalse();
            softAssertions.assertThat(response.jsonPath().getString("books[0].category"))
                    .isEqualTo(book2.getCategory());
        });
    }

    @DisplayName("초대 url을 성공적으로 생성한다.")
    @Test
    void createInvitationUrl() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        CreateInvitationUrlRequest request = new CreateInvitationUrlRequest(1L);

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .body(request)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/book-clubs/invitation")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getString("invitationUrl"))
                    .startsWith("https://bookkbookk.site/join/");
        });
    }

    @DisplayName("초대 url을 성공적으로 조회한다.")
    @Test
    void readInvitationUrl() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        CreateInvitationUrlRequest request = new CreateInvitationUrlRequest(1L);
        String invitationCode = "test";
        BookClubInvitationCode bookClubInvitationCode = new BookClubInvitationCode(request, invitationCode);
        bookClubInvitationCodeRepository.save(bookClubInvitationCode);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .get("/api/book-clubs/invitation/1")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getString("invitationUrl"))
                    .isEqualTo("https://bookkbookk.site/join/test");
        });
    }

    @DisplayName("멤버가 북클럽 초대 url로 북클럽에 참여한다.")
    @Test
    void memberJoinBookClub() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        Member anotherMember = TestDataFactory.createAnotherMember();
        memberRepository.save(anotherMember);

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        CreateInvitationUrlRequest request = new CreateInvitationUrlRequest(bookClub.getId());
        String invitationCode = "test";
        BookClubInvitationCode bookClubInvitationCode = new BookClubInvitationCode(request, invitationCode);
        bookClubInvitationCodeRepository.save(bookClubInvitationCode);

        String accessToken = jwtProvider.createAccessToken(anotherMember.getId());
        JSONObject requestBody = new JSONObject(Map.of("invitationCode", invitationCode));

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .post("/api/book-clubs/join")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getLong("bookClubId")).isEqualTo(bookClub.getId());
        });
    }

    @DisplayName("북클럽에 참여한 멤버가 해당 북클럽 초대 url에 접근해 북클럽에 참여하려 하면 예외가 발생한다.")
    @Test
    void joinedMemberJoinBookClub() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        CreateInvitationUrlRequest request = new CreateInvitationUrlRequest(bookClub.getId());
        String invitationCode = "test";
        BookClubInvitationCode bookClubInvitationCode = new BookClubInvitationCode(request, invitationCode);
        bookClubInvitationCodeRepository.save(bookClubInvitationCode);

        String accessToken = jwtProvider.createAccessToken(member.getId());
        JSONObject requestBody = new JSONObject(Map.of("invitationCode", invitationCode));
        MemberJoinedBookClubException exception = new MemberJoinedBookClubException();

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .post("/api/book-clubs/join")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
            softAssertions.assertThat(response.jsonPath().getString("message")).isEqualTo(exception.getMessage());
        });
    }

    @DisplayName("멤버가 북클럽에 참여하면 북클럽의 책이 멤버 책에 추가된다.")
    @Test
    void bookClubBooksAddToJoinedMember() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        Member another = TestDataFactory.createAnotherMember();
        memberRepository.save(another);

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book1 = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book1);
        Book book2 = TestDataFactory.createBook2(bookClub);
        bookRepository.save(book2);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        CreateInvitationUrlRequest request = new CreateInvitationUrlRequest(bookClub.getId());
        String invitationCode = "test";
        BookClubInvitationCode bookClubInvitationCode = new BookClubInvitationCode(request, invitationCode);
        bookClubInvitationCodeRepository.save(bookClubInvitationCode);

        String accessToken = jwtProvider.createAccessToken(another.getId());
        JSONObject requestBody = new JSONObject(Map.of("invitationCode", invitationCode));

        //when
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .post("/api/book-clubs/join")
                .then().log().all()
                .extract();

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .get("/api/members/books")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getList("books").size()).isEqualTo(2);
        });
    }

}
