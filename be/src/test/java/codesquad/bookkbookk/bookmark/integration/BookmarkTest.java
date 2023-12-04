package codesquad.bookkbookk.bookmark.integration;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.common.error.exception.BookmarkNotFoundException;
import codesquad.bookkbookk.common.error.exception.BookmarkReactionExistsException;
import codesquad.bookkbookk.common.error.exception.BookmarkReactionNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberIsNotBookmarkWriterException;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.common.type.Reaction;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.bookmark.repository.BookmarkRepository;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.chapter.repository.ChapterRepository;
import codesquad.bookkbookk.domain.mapping.entity.BookmarkReaction;
import codesquad.bookkbookk.domain.mapping.repository.BookmarkReactionRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;
import codesquad.bookkbookk.domain.topic.repository.TopicRepository;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class BookmarkTest extends IntegrationTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BookClubRepository bookClubRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    BookmarkReactionRepository bookmarkReactionRepository;
    @Autowired
    JwtProvider jwtProvider;

    @Test
    @DisplayName("북마크를 생성한다.")
    void createBookmark() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book);

        Chapter chapter = TestDataFactory.createChapter1(book);
        chapterRepository.save(chapter);

        Topic topic = TestDataFactory.createTopic1(chapter);
        topicRepository.save(topic);

        JSONObject requestBody = new JSONObject(Map.of("topicId", topic.getId(),
                "title", "title",
                "content", "content"));

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .post("api/bookmarks")
                .then()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        });
    }

    @Test
    @DisplayName("북마크를 수정한다.")
    void updateBookmark() throws InterruptedException {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book);

        Chapter chapter = TestDataFactory.createChapter1(book);
        chapterRepository.save(chapter);

        Topic topic = TestDataFactory.createTopic1(chapter);
        topicRepository.save(topic);

        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        bookmarkRepository.save(bookmark);

        JSONObject requestBody = new JSONObject(Map.of("title", "updated title",
                "content", "updated content"));
        Thread.sleep(1000);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .patch("api/bookmarks/" + bookmark.getId())
                .then()
                .extract();

        // then
        Bookmark actual = bookmarkRepository.findById(bookmark.getId()).orElseThrow(BookmarkNotFoundException::new);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(actual.getTitle()).isEqualTo("updated title");
            softAssertions.assertThat(actual.getUpdateAt()).isNotEqualTo(actual.getCreateAt());
        });
    }

    @Test
    @DisplayName("작성자가 아닌 멤버가 북마크를 수정하려 하면 예외가 발생한다.")
    void NonWriterUpdateBookmark() throws InterruptedException {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        Member another = TestDataFactory.createAnotherMember();
        memberRepository.save(another);
        String accessToken = jwtProvider.createAccessToken(another.getId());

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book);

        Chapter chapter = TestDataFactory.createChapter1(book);
        chapterRepository.save(chapter);

        Topic topic = TestDataFactory.createTopic1(chapter);
        topicRepository.save(topic);

        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        bookmarkRepository.save(bookmark);

        JSONObject requestBody = new JSONObject(Map.of("title", "updated title",
                "content", "updated content"));
        MemberIsNotBookmarkWriterException exception = new MemberIsNotBookmarkWriterException();

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .patch("api/bookmarks/" + bookmark.getId())
                .then()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            softAssertions.assertThat(response.jsonPath().getString("message")).isEqualTo(exception.getMessage());
        });
    }

    @Test
    @DisplayName("북마크를 삭제한다.")
    void deleteBookmark() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book);

        Chapter chapter = TestDataFactory.createChapter1(book);
        chapterRepository.save(chapter);

        Topic topic = TestDataFactory.createTopic1(chapter);
        topicRepository.save(topic);

        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        bookmarkRepository.save(bookmark);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .delete("api/bookmarks/" + bookmark.getId())
                .then()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThatThrownBy(() -> bookmarkRepository.findById(bookmark.getId())
                            .orElseThrow(BookmarkNotFoundException::new))
                    .isInstanceOf(BookmarkNotFoundException.class);
        });
    }

    @Test
    @DisplayName("작성자가 아닌 북마크를 삭제하려 하면 예외가 발생한다.")
    void NonWriterDeleteBookmark() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        Member anothoer = TestDataFactory.createAnotherMember();
        memberRepository.save(anothoer);
        String accessToken = jwtProvider.createAccessToken(anothoer.getId());

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book);

        Chapter chapter = TestDataFactory.createChapter1(book);
        chapterRepository.save(chapter);

        Topic topic = TestDataFactory.createTopic1(chapter);
        topicRepository.save(topic);

        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        bookmarkRepository.save(bookmark);

        MemberIsNotBookmarkWriterException exception = new MemberIsNotBookmarkWriterException();

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .delete("api/bookmarks/" + bookmark.getId())
                .then()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            softAssertions.assertThat(response.jsonPath().getString("message")).isEqualTo(exception.getMessage());
        });
    }

    @Test
    @DisplayName("Bookmark에 리액션을 생성한다.")
    void createBookmarkReaction() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book);

        Chapter chapter = TestDataFactory.createChapter1(book);
        chapterRepository.save(chapter);

        Topic topic = TestDataFactory.createTopic1(chapter);
        topicRepository.save(topic);

        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        bookmarkRepository.save(bookmark);

        JSONObject requestBody = new JSONObject(Map.of("reactionName", "like"));

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .post("api/bookmarks/" + bookmark.getId() + "/reactions")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Member가 같은 Bookmark에 같은 리액션을 반복하여 생성하려 하면 예외가 발생한다.")
    void createSameBookmarkReaction() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book);

        Chapter chapter = TestDataFactory.createChapter1(book);
        chapterRepository.save(chapter);

        Topic topic = TestDataFactory.createTopic1(chapter);
        topicRepository.save(topic);

        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        bookmarkRepository.save(bookmark);

        BookmarkReaction bookmarkReaction = new BookmarkReaction(bookmark, member, Reaction.LIKE);
        bookmarkReactionRepository.save(bookmarkReaction);

        JSONObject requestBody = new JSONObject(Map.of("reactionName", "like"));
        BookmarkReactionExistsException exception = new BookmarkReactionExistsException();

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .post("api/bookmarks/" + bookmark.getId() + "/reactions")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            assertions.assertThat(response.jsonPath().getString("message")).isEqualTo(exception.getMessage());
        });
    }

    @Test
    @DisplayName("Bookmark의 reaction을 삭제한다.")
    void deleteBookmarkReaction() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book);

        Chapter chapter = TestDataFactory.createChapter1(book);
        chapterRepository.save(chapter);

        Topic topic = TestDataFactory.createTopic1(chapter);
        topicRepository.save(topic);

        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        bookmarkRepository.save(bookmark);

        BookmarkReaction bookmarkReaction = new BookmarkReaction(bookmark, member, Reaction.LIKE);
        bookmarkReactionRepository.save(bookmarkReaction);

        JSONObject requestBody = new JSONObject(Map.of("reactionName", "like"));

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .delete("api/bookmarks/" + bookmark.getId() + "/reactions")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Bookmark에 없는 reaction을 삭제하려하면 예외가 발생한다.")
    void deleteUnsavedBookmarkReaction() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book);

        Chapter chapter = TestDataFactory.createChapter1(book);
        chapterRepository.save(chapter);

        Topic topic = TestDataFactory.createTopic1(chapter);
        topicRepository.save(topic);

        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        bookmarkRepository.save(bookmark);

        BookmarkReactionNotFoundException exception = new BookmarkReactionNotFoundException();
        JSONObject requestBody = new JSONObject(Map.of("reactionName", "like"));

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .delete("api/bookmarks/" + bookmark.getId() + "/reactions")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            assertions.assertThat(response.jsonPath().getString("message")).isEqualTo(exception.getMessage());
        });
    }

}
