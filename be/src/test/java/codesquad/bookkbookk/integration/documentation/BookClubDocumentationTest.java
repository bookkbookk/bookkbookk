package codesquad.bookkbookk.integration.documentation;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.common.redis.RedisService;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateInvitationUrlRequest;
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

public class BookClubDocumentationTest extends IntegrationTest {

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
    @DisplayName("북클럽을 생성한다.")
    void createBookClub() throws IOException {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .multiPart("profileImage", File.createTempFile("create", "jpeg"), MediaType.IMAGE_JPEG_VALUE)
                .multiPart("name", "name")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .filter(document("{class-name}/{method-name}",
                        requestParts(
                                partWithName("profileImage").description("북클럽 프로필 이미지 파일"),
                                partWithName("name").description("북클럽 이름")
                        ),
                        responseFields(
                                fieldWithPath("bookClubId").type(JsonFieldType.NUMBER).description("생성된 북클럽 아이디"),
                                fieldWithPath("invitationUrl").type(JsonFieldType.STRING).description("북클럽 초대 URL")
                        )))
                .when()
                .post("/api/book-clubs")
                .then().log().all()
                .extract();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getLong("bookClubId")).isEqualTo(member.getId());
        });
    }

    @DisplayName("멤버의 모든 북클럽들의 정보를 가져온다.")
    @Test
    void readMemberBookClubs() {
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
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("status", "ALL")
                .contentType(ContentType.JSON)
                .filter(document("{class-name}/{method-name}",
                        requestParameters(
                                parameterWithName("status").description("책 상태")
                        ),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("북클럽 아이디"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("북클럽 이름"),
                                fieldWithPath("[].status").type(JsonFieldType.STRING).description("북클럽 상태"),
                                fieldWithPath("[].profileImgUrl")
                                        .type(JsonFieldType.STRING).description("북클럽 프로필 이미지 URL"),
                                fieldWithPath("[].createdTime").type(JsonFieldType.STRING).description("북클럽 생성 시간"),
                                fieldWithPath("[].lastBook")
                                        .type(JsonFieldType.OBJECT).description("북클럽의 마지막 책").optional(),
                                fieldWithPath("[].lastBook.name")
                                        .type(JsonFieldType.STRING).description("북클럽의 마지막 책 이름"),
                                fieldWithPath("[].lastBook.author")
                                        .type(JsonFieldType.STRING).description("북클럽의 마지막 책 저자"),
                                fieldWithPath("[].members").type(JsonFieldType.ARRAY).description("북클럽의 멤버들"),
                                fieldWithPath("[].members[].id")
                                        .type(JsonFieldType.NUMBER).description("북클럽의 멤버 아이디"),
                                fieldWithPath("[].members[].nickname").
                                        type(JsonFieldType.STRING).description("북클럽의 멤버 닉네임"),
                                fieldWithPath("[].members[].profileImgUrl")
                                        .type(JsonFieldType.STRING).description("북클럽의 멤버 프로필 이미지 URL"),
                                fieldWithPath("[].members[].email")
                                        .type(JsonFieldType.STRING).description("북클럽의 멤버 이메일"),
                                fieldWithPath("[].upcomingGatheringDate")
                                        .type(JsonFieldType.STRING).description("북클럽의 곧 다가올 모임 시간").optional(),
                                fieldWithPath("[].closedTime")
                                        .type(JsonFieldType.STRING).description("북클럽이 닫힌 시간").optional()
                        )))
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

    @DisplayName("북클럽 초대 URL을 생성한다.")
    @Test
    void createInvitationUrl() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        CreateInvitationUrlRequest request = new CreateInvitationUrlRequest(bookClub.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .body(request)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .filter(document("{class-name}/{method-name}",
                        requestFields(
                                fieldWithPath("bookClubId").type(JsonFieldType.NUMBER).description("북클럽 아이디")
                        ),
                        responseFields(
                                fieldWithPath("invitationUrl").type(JsonFieldType.STRING).description("생성된 북클럽 초대 URL")
                        )))
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

    @DisplayName("북클럽의 초대 URL을 조회한다.")
    @Test
    void readBookClubInvitationUrl() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        String invitationCode = String.valueOf(UUID.randomUUID());
        redisService.saveInvitationCode(invitationCode, bookClub.getId());

        String accessToken = jwtProvider.createAccessToken(member.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("bookClubId", bookClub.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookClubId").description("조회할 북클럽 아이디")
                        ),
                        responseFields(
                                fieldWithPath("invitationUrl").type(JsonFieldType.STRING).description("북클럽 초대 URL")
                        )))
                .when()
                .get("/api/book-clubs/invitation/{bookClubId}")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getString("invitationUrl"))
                    .startsWith("https://bookkbookk.site/join/");
        });
    }

    @DisplayName("멤버가 북클럽 초대 URL로 북클럽에 참여한다.")
    @Test
    void joinBookClub() {
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
        redisService.saveInvitationCode(invitationCode, bookClub.getId());

        String accessToken = jwtProvider.createAccessToken(anotherMember.getId());
        JSONObject requestBody = new JSONObject(Map.of("invitationCode", invitationCode));

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .filter(document("{class-name}/{method-name}",
                        requestFields(
                                fieldWithPath("invitationCode").type(JsonFieldType.STRING).description("북클럽 초대 코드")
                        ),
                        responseFields(
                                fieldWithPath("bookClubId").type(JsonFieldType.NUMBER).description("참여한 북클럽 아이디")
                        )))
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

    @DisplayName("열린 북클럽 상세 정보를 가져온다.")
    @Test
    void readOpenBookClubDetail() {
        //given
        List<Member> members = TestDataFactory.createMembers(2);
        memberRepository.saveAll(members);
        Member member = members.get(0);
        Member anotherMember = members.get(1);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember1 = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember1);
        BookClubMember bookClubMember2 = new BookClubMember(bookClub, anotherMember);
        bookClubMemberRepository.save(bookClubMember2);

        List<Book> books = TestDataFactory.createBooks(2, bookClub);
        bookRepository.saveAll(books);

        Gathering gathering = TestDataFactory.createGathering(books.get(0));
        gatheringRepository.save(gathering);

        bookClub.updateUpcomingGatheringDate(gathering.getStartTime());
        bookClubRepository.save(bookClub);

        String accessToken = jwtProvider.createAccessToken(anotherMember.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .pathParam("bookClubId", bookClub.getId())
                .contentType(ContentType.JSON)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookClubId").description("북클럽 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("북클럽 아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("북클럽 이름"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("북클럽 상태"),
                                fieldWithPath("profileImgUrl")
                                        .type(JsonFieldType.STRING).description("북클럽 프로필 이미지 URL"),
                                fieldWithPath("createdTime").type(JsonFieldType.STRING).description("북클럽 생성 시간"),
                                fieldWithPath("lastBook")
                                        .type(JsonFieldType.OBJECT).description("북클럽의 마지막 책").optional(),
                                fieldWithPath("lastBook.name")
                                        .type(JsonFieldType.STRING).description("북클럽의 마지막 책 이름"),
                                fieldWithPath("lastBook.author")
                                        .type(JsonFieldType.STRING).description("북클럽의 마지막 책 저자"),
                                fieldWithPath("members").type(JsonFieldType.ARRAY).description("북클럽의 멤버들"),
                                fieldWithPath("members[].id")
                                        .type(JsonFieldType.NUMBER).description("북클럽의 멤버 아이디"),
                                fieldWithPath("members[].nickname").
                                        type(JsonFieldType.STRING).description("북클럽의 멤버 닉네임"),
                                fieldWithPath("members[].profileImgUrl")
                                        .type(JsonFieldType.STRING).description("북클럽의 멤버 프로필 이미지 URL"),
                                fieldWithPath("members[].email")
                                        .type(JsonFieldType.STRING).description("북클럽의 멤버 이메일"),
                                fieldWithPath("upcomingGatheringDate")
                                        .type(JsonFieldType.STRING).description("북클럽의 곧 다가올 모임 시간")
                        )))
                .when()
                .get("/api/book-clubs/{bookClubId}")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getList("members").size()).isEqualTo(2);
            softAssertions.assertThat(response.jsonPath().getMap("lastBook")).isNotNull();
            softAssertions.assertThat(response.jsonPath().getString("upcomingGatheringDate")).isNotNull();
        });
    }

    @DisplayName("닫힌 북클럽 상세 정보를 가져온다.")
    @Test
    void readClosedBookClubDetail() {
        //given
        List<Member> members = TestDataFactory.createMembers(2);
        memberRepository.saveAll(members);
        Member member = members.get(0);
        Member anotherMember = members.get(1);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember1 = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember1);
        BookClubMember bookClubMember2 = new BookClubMember(bookClub, anotherMember);
        bookClubMemberRepository.save(bookClubMember2);

        List<Book> books = TestDataFactory.createBooks(2, bookClub);
        bookRepository.saveAll(books);

        bookClub.close();
        bookClubRepository.save(bookClub);

        String accessToken = jwtProvider.createAccessToken(anotherMember.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .pathParam("bookClubId", bookClub.getId())
                .contentType(ContentType.JSON)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookClubId").description("북클럽 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("북클럽 아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("북클럽 이름"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("북클럽 상태"),
                                fieldWithPath("profileImgUrl")
                                        .type(JsonFieldType.STRING).description("북클럽 프로필 이미지 URL"),
                                fieldWithPath("createdTime").type(JsonFieldType.STRING).description("북클럽 생성 시간"),
                                fieldWithPath("lastBook")
                                        .type(JsonFieldType.OBJECT).description("북클럽의 마지막 책").optional(),
                                fieldWithPath("lastBook.name")
                                        .type(JsonFieldType.STRING).description("북클럽의 마지막 책 이름"),
                                fieldWithPath("lastBook.author")
                                        .type(JsonFieldType.STRING).description("북클럽의 마지막 책 저자"),
                                fieldWithPath("members").type(JsonFieldType.ARRAY).description("북클럽의 멤버들"),
                                fieldWithPath("members[].id")
                                        .type(JsonFieldType.NUMBER).description("북클럽의 멤버 아이디"),
                                fieldWithPath("members[].nickname").
                                        type(JsonFieldType.STRING).description("북클럽의 멤버 닉네임"),
                                fieldWithPath("members[].profileImgUrl")
                                        .type(JsonFieldType.STRING).description("북클럽의 멤버 프로필 이미지 URL"),
                                fieldWithPath("members[].email")
                                        .type(JsonFieldType.STRING).description("북클럽의 멤버 이메일"),
                                fieldWithPath("closedTime")
                                        .type(JsonFieldType.STRING).description("북클럽이 닫힌 시간")
                        )))
                .when()
                .get("/api/book-clubs/{bookClubId}")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getList("members").size()).isEqualTo(2);
            softAssertions.assertThat(response.jsonPath().getMap("lastBook")).isNotNull();
            softAssertions.assertThat(response.jsonPath().getString("closedTime")).isNotNull();
        });
    }

    @Test
    @DisplayName("북클럽의 책들을 슬라이스 나눠서 보내준다.")
    void readBookClubBooks() {
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
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .pathParam("bookClubId", bookClub.getId())
                .queryParam("cursor", 0)
                .queryParam("size", 1)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookClubId").description("북클럽 아이디")
                        ),
                        requestParameters(
                                parameterWithName("cursor").description("페이지 커서"),
                                parameterWithName("size").description("페이지 크기")
                        ),
                        responseFields(
                                fieldWithPath("books").type(JsonFieldType.ARRAY).description("책 정보"),
                                fieldWithPath("books[].id").type(JsonFieldType.NUMBER).description("책 아이디"),
                                fieldWithPath("books[].title").type(JsonFieldType.STRING).description("책 제목"),
                                fieldWithPath("books[].cover").type(JsonFieldType.STRING).description("책 표지 이미지 URL"),
                                fieldWithPath("books[].author").type(JsonFieldType.STRING).description("책 저자"),
                                fieldWithPath("books[].category").type(JsonFieldType.STRING).description("책 카테고리"),
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 유무")
                        )))
                .when()
                .get("/api/book-clubs/{bookClubId}/books")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getBoolean("hasNext")).isTrue();
            softAssertions.assertThat(response.jsonPath().getString("books[0].author"))
                    .isEqualTo(books.get(0).getAuthor());
        });
    }

    @DisplayName("북클럽의 모든 모임 정보들을 조회한다.")
    @Test
    void readBookClubGatherings() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        List<Book> books = TestDataFactory.createBooks(3, bookClub);
        bookRepository.saveAll(books);

        List<Gathering> gatherings1 = TestDataFactory.createGatherings(3, books.get(0));
        gatheringRepository.saveAll(gatherings1);
        List<Gathering> gatherings2 = TestDataFactory.createGatherings(2, books.get(1));
        gatheringRepository.saveAll(gatherings2);
        List<Gathering> gatherings3 = TestDataFactory.createGatherings(1, books.get(2));
        gatheringRepository.saveAll(gatherings3);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .pathParam("bookClubId", bookClub.getId())
                .contentType(ContentType.JSON)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookClubId").description("북클럽 아이디")
                        ),
                        responseFields(
                                fieldWithPath("[].book.title").type(JsonFieldType.STRING).description("책 제목"),
                                fieldWithPath("[].book.cover").type(JsonFieldType.STRING).description("책 표지 이미지 URL"),
                                fieldWithPath("[].book.author").type(JsonFieldType.STRING).description("책 저자"),
                                fieldWithPath("[].book.category").type(JsonFieldType.STRING).description("책 카테고리"),
                                fieldWithPath("[].book.status").type(JsonFieldType.STRING).description("책 상태"),
                                fieldWithPath("[].dateTime").type(JsonFieldType.STRING).description("모임 시간"),
                                fieldWithPath("[].place").type(JsonFieldType.STRING).description("모임 장소")
                        )))
                .when()
                .get("/api/book-clubs/{bookClubId}/gatherings")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getList("").size()).isEqualTo(6);
        });
    }

    @Test
    @DisplayName("북클럽의 모임들을 만는다.")
    void createGatherings() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        bookClubMemberRepository.save(new BookClubMember(bookClub, member));

        Book book = TestDataFactory.createBook(bookClub);
        bookRepository.save(book);

        String accessToken = jwtProvider.createAccessToken(member.getId());
        Map<String, Object> requestBody = Map.of("bookId", book.getId(),
                "gatherings", TestDataFactory.createGatheringRequestGatherings());

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("bookClubId", bookClub.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookClubId").description("북클럽 아이디")
                        ),
                        requestFields(
                                fieldWithPath("bookId").type(JsonFieldType.NUMBER).description("책 아이디"),
                                fieldWithPath("gatherings").type(JsonFieldType.ARRAY).description("모임들"),
                                fieldWithPath("gatherings[].dateTime").type(JsonFieldType.STRING).description("모임 시간"),
                                fieldWithPath("gatherings[].place").type(JsonFieldType.STRING).description("모임 장소")
                        )
                        ))
                .when()
                .post("/api/book-clubs/{bookClubId}/gatherings")
                .then().log().all()
                .extract();

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

}
