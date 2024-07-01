package codesquad.bookkbookk.integration.scenario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;

import codesquad.bookkbookk.common.error.exception.ApiException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookRequest;
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

public class BookTest extends IntegrationTest {

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
    @DisplayName("책 추가 권한이 없는 멤버가 책을 추가했을 때 에러가 발생한다.")
    void createBookNotInBookClub() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        CreateBookRequest createBookRequest = CreateBookRequest.builder()
                .isbn("123123123")
                .bookClubId(bookClub.getId())
                .title("책")
                .cover("image.image")
                .author("감귤")
                .category("추리")
                .build();

        MemberNotInBookClubException exception = new MemberNotInBookClubException();

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .body(createBookRequest)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .post("/api/books")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            softAssertions.assertThat(response.jsonPath().getObject("", ApiException.class).getMessage())
                    .isEqualTo(exception.getMessage());
        });
    }

    @DisplayName("책의 페이지로 북마크를 조회할 때 startPage가 endPage보다 크면 빈 리스트를 보낸다.")
    @Test
    void readBookmarksWithRevertedPages() {
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

        int startPage = 17;
        int endPage = 3;

        //when
        Response response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("startPage", startPage)
                .queryParam("endPage", endPage)
                .when()
                .get("/api/books/" + book.getId() + "/bookmarks")
                .then().log().all()
                .extract().response();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.jsonPath().getList("").size()).isEqualTo(0);
        });
    }

    @DisplayName("책의 북마크 필터링 조건에서 시간 정보가 없으면 페이지로만 필터링한다.")
    @Test
    void readBookmarksWithOnlyPageFilter() throws IOException {
        // given
        String sql = new String(Files.readAllBytes(Paths.get("src/test/resources/sql/readBookmarksWithUpdatedTime.sql")));
        jdbcTemplate.execute(sql);
        String accessToken = jwtProvider.createAccessToken(1L);

        //when
        Response response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .pathParam("bookId",1L)
                .queryParam("startPage", 32)
                .queryParam("endPage",95)
                .when()
                .get("/api/books/{bookId}/bookmarks")
                .then().log().all()
                .extract().response();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.jsonPath().getList("").size()).isEqualTo(6);
            softAssertions.assertThat(response.jsonPath().getInt("[0].page")).isEqualTo(40);
            softAssertions.assertThat(response.jsonPath().getInt("[5].page")).isEqualTo(90);
        });
    }

    @DisplayName("책의 북마크 필터링 조건에서 endPage랑 endTime이 없이 필터링한다.")
    @Test
    void readBookmarksWithFilter() throws IOException {
        // given
        String sql = new String(Files.readAllBytes(Paths.get("src/test/resources/sql/readBookmarksWithUpdatedTime.sql")));
        jdbcTemplate.execute(sql);
        String accessToken = jwtProvider.createAccessToken(1L);
        Instant startTime = Instant.parse("2024-02-12T12:32:30Z");

        //when
        Response response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .pathParam("bookId",1L)
                .queryParam("startPage", 90)
                .queryParam("startTime", startTime.toString())
                .when()
                .get("/api/books/{bookId}/bookmarks")
                .then().log().all()
                .extract().response();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.jsonPath().getList("").size()).isEqualTo(8);
            softAssertions.assertThat(response.jsonPath().getString("[0].updatedTime"))
                    .isEqualTo("2024-02-20T00:00:00Z");
            softAssertions.assertThat(response.jsonPath().getInt("[7].page")).isEqualTo(130);
        });
    }

}
