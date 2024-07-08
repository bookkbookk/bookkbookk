package codesquad.bookkbookk.integration.documentation;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.jdbc.Sql;

import codesquad.bookkbookk.common.error.exception.BookNotFoundException;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookRequest;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookResponse;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.bookmark.repository.BookmarkRepository;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.chapter.repository.ChapterRepository;
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;
import codesquad.bookkbookk.domain.topic.repository.TopicRepository;
import codesquad.bookkbookk.util.IntegrationTest;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class BookDocumentationTest extends IntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookClubRepository bookClubRepository;

    @Autowired
    private BookClubMemberRepository bookClubMemberRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("책을 성공적으로 생성한다.")
    void createBook() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        CreateBookRequest createBookRequest = CreateBookRequest.builder()
                .isbn("9791169210607")
                .bookClubId(bookClub.getId())
                .title("책")
                .cover("image.image")
                .author("감귤")
                .category("추리")
                .build();

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .body(createBookRequest)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .filter(document("{class-name}/{method-name}",
                        requestFields(
                                fieldWithPath("bookClubId").type(JsonFieldType.NUMBER).description("북클럽 아이디"),
                                fieldWithPath("isbn").type(JsonFieldType.STRING).description("isbn 번호"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("책 제목"),
                                fieldWithPath("cover").type(JsonFieldType.STRING).description("책 커버 이미지 URL"),
                                fieldWithPath("author").type(JsonFieldType.STRING).description("작가 이름"),
                                fieldWithPath("category").optional().type(JsonFieldType.STRING).description("카테고리")
                        ),
                        responseFields(
                                fieldWithPath("createdBookId").type(JsonFieldType.NUMBER).description("생성된 책 아이디")
                        )))
                .when()
                .post("/api/books")
                .then().log().all()
                .extract();

        //then
        CreateBookResponse createBookResponse = response.jsonPath().getObject("", CreateBookResponse.class);
        Book expectedBook = bookRepository.findById(createBookResponse.getCreatedBookId())
                .orElseThrow(BookNotFoundException::new);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(expectedBook.getIsbn()).isEqualTo(createBookRequest.getIsbn());
            softAssertions.assertThat(expectedBook.getCategory()).isEqualTo(createBookRequest.getCategory());
            softAssertions.assertThat(expectedBook.getTitle()).isEqualTo(createBookRequest.getTitle());
        });
    }

    @Test
    @DisplayName("책의 상태를 변경한다.")
    void updateBookStatus() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        bookRepository.save(book);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        Map<String, Integer> requestMap = Map.of("statusId", 2);
        JSONObject requestBody = new JSONObject(requestMap);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookId").description("변경할 책 아이디")
                        ),
                        requestFields(
                                fieldWithPath("statusId").type(JsonFieldType.NUMBER).description("변경할 상태 아이디")
                        ),
                        responseFields(
                                fieldWithPath("bookId").type(JsonFieldType.NUMBER).description("책 아이디"),
                                fieldWithPath("statusId").type(JsonFieldType.NUMBER).description("변경된 상태 아이디")
                        )))
                .when()
                .patch("/api/books/{bookId}", book.getId())
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getLong("bookId")).isEqualTo(book.getId());
            softAssertions.assertThat(response.jsonPath().getInt("statusId"))
                    .isEqualTo(requestMap.get("statusId"));
        });
    }

    @DisplayName("책 아이디로 챕터를 조회한다.")
    @Test
    void readChaptersWithBookId() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        bookRepository.save(book);

        List<Chapter> chapters = TestDataFactory.createChapters(2, book);
        chapterRepository.saveAll(chapters);

        List<Topic> topics = TestDataFactory.createTopics(2, chapters.get(0));
        topicRepository.saveAll(topics);

        List<Bookmark> bookmarks1 = TestDataFactory.createBookmarks(5, member, topics.get(0));
        bookmarkRepository.saveAll(bookmarks1);
        List<Bookmark> bookmarks2 = TestDataFactory.createBookmarks(4, member, topics.get(1));
        bookmarkRepository.saveAll(bookmarks2);

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .pathParam("bookId", book.getId())
                .queryParam("statusId", 0)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookId").description("검색할 책 아이디")
                        ),
                        requestParameters(
                                parameterWithName("statusId").description("검색할 챕터 상태 아이디")
                        ),
                        responseFields(
                                fieldWithPath("[].chapterId").type(JsonFieldType.NUMBER).description("챕터 아이디"),
                                fieldWithPath("[].title").type(JsonFieldType.STRING).description("챕터 타이틀"),
                                fieldWithPath("[].statusId").type(JsonFieldType.NUMBER).description("챕터 상태 아이디"),
                                fieldWithPath("[].topics").type(JsonFieldType.ARRAY).description("챕터의 토픽들"),
                                fieldWithPath("[].topics[].topicId").type(JsonFieldType.NUMBER).description("토픽 아이디"),
                                fieldWithPath("[].topics[].title").type(JsonFieldType.STRING).description("토픽 타이틀"),
                                fieldWithPath("[].topics[].recentBookmark")
                                        .type(JsonFieldType.OBJECT).description("토픽의 최근 북마크"),
                                fieldWithPath("[].topics[].recentBookmark.authorProfileImgUrl")
                                        .type(JsonFieldType.STRING).description("북마크의 작성자 프로필 이미지 URL"),
                                fieldWithPath("[].topics[].recentBookmark.content")
                                        .type(JsonFieldType.STRING).description("북마크의 내용")
                        )))
                .when()
                .get("/api/books/{bookId}/chapters")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getString("[0].title"))
                    .isEqualTo(chapters.get(0).getTitle());
            softAssertions.assertThat(response.jsonPath().getString("[0].topics[1].recentBookmark.content"))
                    .isNotNull();
            softAssertions.assertThat(response.jsonPath().getList("[1].topics")).isEmpty();
        });
    }

    @DisplayName("페이지로 책의 북마크들을 조회한다.")
    @Test
    void readBookmarksWithPages() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        BookClubMember bookClubMember = new BookClubMember(bookClub, member);
        bookClubMemberRepository.save(bookClubMember);

        Book book = TestDataFactory.createBook(bookClub);
        bookRepository.save(book);

        Chapter chapter = TestDataFactory.createChapter(book);
        chapterRepository.save(chapter);

        Topic topic = TestDataFactory.createTopic(chapter);
        topicRepository.save(topic);

        List<Bookmark> bookmarks = TestDataFactory.createBookmarks(20, member, topic);
        bookmarkRepository.saveAll(bookmarks);

        int startPage = 3;
        int endPage = 17;

        //when
        Response response = RestAssured
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .pathParam("bookId", book.getId())
                .queryParam("startPage", startPage)
                .queryParam("endPage", endPage)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookId").description("검색할 책 아이디")
                        ),
                        requestParameters(
                                parameterWithName("startPage").description("검색 시작 페이지"),
                                parameterWithName("endPage").description("검색 마지막 페이지")
                        ),
                        responseFields(
                                fieldWithPath("[].bookmarkId").type(JsonFieldType.NUMBER).description("북마크 아이디"),
                                fieldWithPath("[].author").type(JsonFieldType.OBJECT).description("북마크 글쓴이 정보"),
                                fieldWithPath("[].author.memberId").type(JsonFieldType.NUMBER)
                                        .description("글쓴이 멤버 아이디"),
                                fieldWithPath("[].author.nickname").type(JsonFieldType.STRING).description("글쓴이 닉네임"),
                                fieldWithPath("[].author.profileImageUrl").type(JsonFieldType.STRING)
                                        .description("글쓴이 프로필 이미지 URL"),
                                fieldWithPath("[].page").type(JsonFieldType.NUMBER).description("북마크 페이지"),
                                fieldWithPath("[].createdTime").type(JsonFieldType.STRING).description("북마크 생성 시간"),
                                fieldWithPath("[].updatedTime").type(JsonFieldType.STRING).description("북마크 수정 시간"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("북마크 내용")
                        )))
                .when()
                .get("/api/books/{bookId}/bookmarks")
                .then().log().all()
                .extract().response();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.jsonPath().getList("").size()).isEqualTo(endPage - startPage + 1);
            softAssertions.assertThat(response.jsonPath().getInt("[0].page")).isEqualTo(startPage);
            softAssertions.assertThat(response.jsonPath().getInt("[" + (endPage - startPage) + "].page")).isEqualTo(endPage);
        });
    }

    @Sql("classpath:sql/readBookmarksWithUpdatedTime.sql")
    @DisplayName("책의 북마크들을 필터링해서 조회한다.")
    @Test
    void readBookmarksWithFilter() {
        // given
        String accessToken = jwtProvider.createAccessToken(1L);
        Instant startTime = Instant.parse("2024-02-03T00:00:00Z");
        Instant endTime = Instant.parse("2024-02-17T12:00:00Z");

        //when
        Response response = RestAssured
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .pathParam("bookId", 1L)
                .queryParam("startTime", startTime.toString())
                .queryParam("endTime", endTime.toString())
                .queryParam("startPage", 50)
                .queryParam("endPage", 190)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookId").description("검색할 책 아이디")
                        ),
                        requestParameters(
                                parameterWithName("startTime").description("검색 시작 시간"),
                                parameterWithName("endTime").description("검색 마지막 시간"),
                                parameterWithName("startPage").description("검색 시작 페이지"),
                                parameterWithName("endPage").description("검색 마지막 페이지")
                        ),
                        responseFields(
                                fieldWithPath("[].bookmarkId").type(JsonFieldType.NUMBER).description("북마크 아이디"),
                                fieldWithPath("[].author").type(JsonFieldType.OBJECT).description("북마크 글쓴이 정보"),
                                fieldWithPath("[].author.memberId").type(JsonFieldType.NUMBER)
                                        .description("글쓴이 멤버 아이디"),
                                fieldWithPath("[].author.nickname").type(JsonFieldType.STRING).description("글쓴이 닉네임"),
                                fieldWithPath("[].author.profileImageUrl").type(JsonFieldType.STRING)
                                        .description("글쓴이 프로필 이미지 URL"),
                                fieldWithPath("[].page").type(JsonFieldType.NUMBER).description("북마크 페이지"),
                                fieldWithPath("[].createdTime").type(JsonFieldType.STRING).description("북마크 생성 시간"),
                                fieldWithPath("[].updatedTime").type(JsonFieldType.STRING).description("북마크 수정 시간"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("북마크 내용")
                        )))
                .when()
                .get("/api/books/{bookId}/bookmarks")
                .then().log().all()
                .extract().response();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.jsonPath().getList("").size()).isEqualTo(13);
            softAssertions.assertThat(response.jsonPath().getString("[0].updatedTime"))
                    .isEqualTo("2024-02-17T00:00:00Z");
            softAssertions.assertThat(response.jsonPath().getString("[12].updatedTime"))
                    .isEqualTo("2024-02-05T00:00:00Z");
            softAssertions.assertThat(response.jsonPath().getInt("[12].page")).isEqualTo(50);
        });
    }

}
