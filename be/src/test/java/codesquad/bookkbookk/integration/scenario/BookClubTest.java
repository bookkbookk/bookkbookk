package codesquad.bookkbookk.integration.scenario;

import java.util.List;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.error.exception.BookNotFoundException;
import codesquad.bookkbookk.common.error.exception.InvitationCodeNotSavedException;
import codesquad.bookkbookk.common.error.exception.MemberJoinedBookClubException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.common.redis.RedisService;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;
import codesquad.bookkbookk.domain.gathering.repository.GatheringRepository;
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.util.IntegrationTest;
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
    private GatheringRepository gatheringRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private RedisService redisService;

    @Test
    @DisplayName("북클럽의 책들의 마지막 slice를 가져온다.")
    void readBookClubBookLastSlice() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        List<Book> books = TestDataFactory.createBooks(2, bookClub);
        bookRepository.saveAll(books);

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
                    .isEqualTo(books.get(1).getCategory());
        });
    }

    @DisplayName("북클럽에 참여한 멤버가 해당 북클럽 초대 url에 접근해 북클럽에 참여하려 하면 예외가 발생한다.")
    @Test
    void joinedMemberJoinBookClub() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        String invitationCode = "test";
        redisService.saveInvitationCode(invitationCode, bookClub.getId());

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

    @DisplayName("멤버가 저장되지 않은 invitation code를 사용하여 book club에 가입하려하면 예외가 발생한다.")
    @Test
    void memberJoinBookClubWithUnsavedInvitationCode() {
        //given
        List<Member> members = TestDataFactory.createMembers(2);
        memberRepository.saveAll(members);
        Member member = members.get(0);
        Member anotherMember = members.get(1);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        String invitationCode = "test";

        String accessToken = jwtProvider.createAccessToken(anotherMember.getId());
        JSONObject requestBody = new JSONObject(Map.of("invitationCode", invitationCode));
        InvitationCodeNotSavedException exception = new InvitationCodeNotSavedException();

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
            softAssertions.assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            softAssertions.assertThat(response.jsonPath().getString("message")).isEqualTo(exception.getMessage());
        });
    }

    @DisplayName("멤버가 북클럽에 참여하면 북클럽의 책이 멤버 책에 추가된다.")
    @Test
    void bookClubBooksAddToJoinedMember() {
        //given
        List<Member> members = TestDataFactory.createMembers(2);
        memberRepository.saveAll(members);
        Member member = members.get(0);
        Member anotherMember = members.get(1);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        List<Book> books = TestDataFactory.createBooks(2, bookClub);
        bookRepository.saveAll(books);

        String invitationCode = "test";
        redisService.saveInvitationCode(invitationCode, bookClub.getId());

        String accessToken = jwtProvider.createAccessToken(anotherMember.getId());
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

    @Test
    @DisplayName("북클럽에 소속되지 않은 멤버가 모임을 만들려하면 예외가 발생한다.")
    void MemberNotInBookClubCreateGathering() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook(bookClub);
        bookRepository.save(book);

        String accessToken = jwtProvider.createAccessToken(member.getId());
        Map<String, Object> requestBody = Map.of("bookId", book.getId(),
                "gatherings", List.of(Map.of("place", "codesquad", "dateTime", "2023-12-25T13:30:00Z")));
        MemberNotInBookClubException exception = new MemberNotInBookClubException();

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/book-clubs/" + bookClub.getId() + "/gatherings")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            assertions.assertThat(response.jsonPath().getString("message")).isEqualTo(exception.getMessage());
        });
    }

    @Test
    @DisplayName("모임을 만들 때 책을 조회하지 못하면 예외가 발생한다.")
    void createGatheringWithNonSavedBook() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        bookClubMemberRepository.save(new BookClubMember(bookClub, member));
        String accessToken = jwtProvider.createAccessToken(member.getId());
        Map<String, Object> requestBody = Map.of("bookId", 1L,
                "gatherings", List.of(Map.of("place", "codesquad", "dateTime", "2023-12-25T13:30:00Z")));
        BookNotFoundException exception = new BookNotFoundException();

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/book-clubs/" + bookClub.getId() + "/gatherings")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            assertions.assertThat(response.jsonPath().getString("message")).isEqualTo(exception.getMessage());
        });
    }

    @DisplayName("멤버의 열린 북클럽들의 정보를 가져온다.")
    @Test
    void readMemberOpenBookClub() {
        //given
        List<Member> members = TestDataFactory.createMembers(2);
        memberRepository.saveAll(members);
        Member member = members.get(0);
        Member anotherMember = members.get(1);

        List<BookClub> bookClubs = TestDataFactory.createBookClubs(3, member);
        bookClubs.get(1).close();
        bookClubRepository.saveAll(bookClubs);

        List<BookClubMember> bookClubMembers = List.of(new BookClubMember(bookClubs.get(0), member),
                new BookClubMember(bookClubs.get(1), member),
                new BookClubMember(bookClubs.get(2), member),
                new BookClubMember(bookClubs.get(2), anotherMember));
        bookClubMemberRepository.saveAll(bookClubMembers);

        BookClub bookclub = bookClubs.get(0);

        Book book = TestDataFactory.createBook(bookclub);
        bookRepository.save(book);

        Gathering gathering = TestDataFactory.createGathering(book);
        gatheringRepository.save(gathering);

        bookclub.updateUpcomingGatheringDate(gathering.getStartTime());
        bookClubRepository.save(bookclub);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("status", "open")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/book-clubs")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getList("[1].members").size()).isEqualTo(2);
            softAssertions.assertThat(response.jsonPath().getMap("[0].lastBook")).isNotNull();
            softAssertions.assertThat(response.jsonPath().getString("[0].upcomingGatheringDate")).isNotNull();
            softAssertions.assertThat(response.jsonPath().getString("[1].upcomingGatheringDate")).isNull();
        });
    }

    @DisplayName("멤버의 닫힌 북클럽들의 정보를 가져온다.")
    @Test
    void readMemberClosedBookClub() {
        //given
        List<Member> members = TestDataFactory.createMembers(2);
        memberRepository.saveAll(members);
        Member member = members.get(0);
        Member anotherMember = members.get(1);

        List<BookClub> bookClubs = TestDataFactory.createBookClubs(3, member);
        bookClubs.get(0).close();
        bookClubs.get(1).close();
        bookClubRepository.saveAll(bookClubs);

        List<BookClubMember> bookClubMembers = List.of(new BookClubMember(bookClubs.get(0), member),
                new BookClubMember(bookClubs.get(1), member),
                new BookClubMember(bookClubs.get(1), anotherMember),
                new BookClubMember(bookClubs.get(2), member));
        bookClubMemberRepository.saveAll(bookClubMembers);

        Book book1 = TestDataFactory.createBook(bookClubs.get(0));
        bookRepository.save(book1);
        Book book2 = TestDataFactory.createBook(1, bookClubs.get(1));
        bookRepository.save(book2);

        Gathering gathering = TestDataFactory.createGathering(book1);
        gatheringRepository.save(gathering);

        bookClubs.get(0).updateUpcomingGatheringDate(gathering.getStartTime());
        bookClubRepository.save(bookClubs.get(0));

        String accessToken = jwtProvider.createAccessToken(member.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("status", "closed")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/book-clubs")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getList("[1].members").size()).isEqualTo(2);
            softAssertions.assertThat(response.jsonPath().getMap("[0].lastBook")).isNotNull();
            softAssertions.assertThat(response.jsonPath().getMap("[1].lastBook")).isNotNull();
            softAssertions.assertThat(response.jsonPath().getString("[0].closedTime")).isNotNull();
            softAssertions.assertThat(response.jsonPath().getString("[1].closedTime")).isNotNull();
        });
    }

    @DisplayName("멤버의 모든 북클럽들의 정보를 가져올 때 Requst Paramter로 Status를 입력하지 않으면 모든 BookClub에 대한 정보를 가져온다.")
    @Test
    void readMemberBookClubsWithoutStatus() {
        //given
        List<Member> members = TestDataFactory.createMembers(2);
        memberRepository.saveAll(members);
        Member member = members.get(0);
        Member anotherMember = members.get(1);

        List<BookClub> bookClubs = TestDataFactory.createBookClubs(5, member);
        bookClubs.get(1).close();
        bookClubs.get(4).close();
        bookClubRepository.saveAll(bookClubs);

        List<BookClubMember> bookClubMembers = List.of(new BookClubMember(bookClubs.get(0), member),
                new BookClubMember(bookClubs.get(1), member),
                new BookClubMember(bookClubs.get(2), member),
                new BookClubMember(bookClubs.get(2), anotherMember),
                new BookClubMember(bookClubs.get(3), member),
                new BookClubMember(bookClubs.get(3), anotherMember),
                new BookClubMember(bookClubs.get(4), member));
        bookClubMemberRepository.saveAll(bookClubMembers);

        Book book1 = TestDataFactory.createBook(1, bookClubs.get(0));
        bookRepository.save(book1);
        Book book2 = TestDataFactory.createBook(2, bookClubs.get(1));
        bookRepository.save(book2);

        Gathering gathering = TestDataFactory.createGathering(book1);
        gatheringRepository.save(gathering);

        bookClubs.get(0).updateUpcomingGatheringDate(gathering.getStartTime());
        bookClubRepository.save(bookClubs.get(0));

        String accessToken = jwtProvider.createAccessToken(member.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/book-clubs")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getList("$").size()).isEqualTo(5);
            softAssertions.assertThat(response.jsonPath().getMap("[0].lastBook")).isNotNull();
            softAssertions.assertThat(response.jsonPath().getList("[2].members").size()).isEqualTo(2);
            softAssertions.assertThat(response.jsonPath().getString("[0].upcomingGatheringDate")).isNotNull();
            softAssertions.assertThat(response.jsonPath().getString("[4].closedTime")).isNotNull();
        });
    }

}
