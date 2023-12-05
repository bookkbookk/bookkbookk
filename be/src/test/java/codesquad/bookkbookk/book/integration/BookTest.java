package codesquad.bookkbookk.book.integration;

import java.util.List;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.common.error.exception.ApiException;
import codesquad.bookkbookk.common.error.exception.BookNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
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

    @Test
    @DisplayName("책을 성공적으로 생성한다.")
    void createBook() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub();
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
    @DisplayName("책 추가 권한이 없는 멤버가 책을 추가했을 때 에러가 발생한다.")
    void createBookNotInBookClub() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub();
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

    @Test
    @DisplayName("책의 상태를 변경한다.")
    void updateBookStatus() throws JSONException {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        Map<String, Integer> requestMap = Map.of("statusId", 2);
        JSONObject requestBody = new JSONObject(requestMap);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .patch("/api/books/" + book.getId())
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

    @DisplayName("챕터를 조회한다.")
    @Test
    void readChapter() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book);

        List<Chapter> chapters = List.of(TestDataFactory.createChapter1(book),
                TestDataFactory.createChapter2(book));
        chapterRepository.saveAll(chapters);

        List<Topic> topics = List.of(TestDataFactory.createTopic1(chapters.get(0)),
                TestDataFactory.createTopic2(chapters.get(0)));
        topicRepository.saveAll(topics);

        List<Bookmark> bookmarks1 = TestDataFactory.createBookmarks(5, member, topics.get(0));
        bookmarkRepository.saveAll(bookmarks1);
        List<Bookmark> bookmarks2 = TestDataFactory.createBookmarks(4, member, topics.get(1));
        bookmarkRepository.saveAll(bookmarks2);


        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("statusId", 0)
                .when()
                .get("/api/books/" + book.getId() + "/chapters")
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

}
