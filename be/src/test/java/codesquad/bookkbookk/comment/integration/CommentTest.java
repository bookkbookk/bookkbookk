package codesquad.bookkbookk.comment.integration;

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
import codesquad.bookkbookk.common.error.exception.CommentNotFoundException;
import codesquad.bookkbookk.common.error.exception.CommentReactionExistsException;
import codesquad.bookkbookk.common.error.exception.CommentReactionNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberIsNotCommentWriterException;
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
import codesquad.bookkbookk.domain.comment.data.entity.Comment;
import codesquad.bookkbookk.domain.comment.repository.CommentRepository;
import codesquad.bookkbookk.domain.mapping.entity.CommentReaction;
import codesquad.bookkbookk.domain.mapping.repository.CommentReactionRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;
import codesquad.bookkbookk.domain.topic.repository.TopicRepository;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class CommentTest extends IntegrationTest {

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
    CommentRepository commentRepository;
    @Autowired
    CommentReactionRepository commentReactionRepository;
    @Autowired
    JwtProvider jwtProvider;

    @Test
    @DisplayName("코멘트를 생성한다.")
    void createComment() {
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

        JSONObject requestBody = new JSONObject(Map.of("bookmarkId", bookmark.getId(),
                "content", "content"));

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .post("api/comments")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        });
    }

    @Test
    @DisplayName("코멘트를 수정한다.")
    void updateComment() throws InterruptedException {
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

        Comment comment = TestDataFactory.createComment(bookmark, member);
        commentRepository.save(comment);

        JSONObject requestBody = new JSONObject(Map.of("content", "updated content"));
        Thread.sleep(1000);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .patch("api/comments/" + comment.getId())
                .then().log().all()
                .extract();

        // then
        Comment actual = commentRepository.findById(comment.getId()).orElseThrow(CommentNotFoundException::new);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(actual.getContent()).isEqualTo("updated content");
            softAssertions.assertThat(actual.getUpdateAt()).isNotEqualTo(actual.getCreateAt());
        });
    }

    @Test
    @DisplayName("작성자가 아닌 멤버가 코멘트를 수정하려 하면 예외가 발생한다.")
    void NonWriterUpdateComment() {
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

        Comment comment = TestDataFactory.createComment(bookmark, member);
        commentRepository.save(comment);

        JSONObject requestBody = new JSONObject(Map.of("content", "updated content"));
        MemberIsNotCommentWriterException exception = new MemberIsNotCommentWriterException();

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .patch("api/comments/" + comment.getId())
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            softAssertions.assertThat(response.jsonPath().getString("message")).isEqualTo(exception.getMessage());
        });
    }

    @Test
    @DisplayName("코멘트를 삭제한다.")
    void deleteComment() {
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

        Comment comment = TestDataFactory.createComment(bookmark, member);
        commentRepository.save(comment);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .delete("api/comments/" + comment.getId())
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThatThrownBy(() -> commentRepository.findById(comment.getId())
                            .orElseThrow(CommentNotFoundException::new))
                    .isInstanceOf(CommentNotFoundException.class);
        });
    }

    @Test
    @DisplayName("작성자가 아닌 코멘트를 삭제하려 하면 예외가 발생한다.")
    void NonWriterDeleteComment() {
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

        Comment comment = TestDataFactory.createComment(bookmark, member);
        commentRepository.save(comment);

        MemberIsNotCommentWriterException exception = new MemberIsNotCommentWriterException();

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .delete("api/comments/" + comment.getId())
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            softAssertions.assertThat(response.jsonPath().getString("message")).isEqualTo(exception.getMessage());
        });
    }

    @Test
    @DisplayName("Comment의 리액션을 생성한다.")
    void createCommentReaction() {
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

        Comment comment = TestDataFactory.createComment(bookmark, member);
        commentRepository.save(comment);

        JSONObject requestBody = new JSONObject(Map.of("reactionName", "like"));

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .post("api/comments/" + comment.getId() + "/reactions")
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

        Comment comment = TestDataFactory.createComment(bookmark, member);
        commentRepository.save(comment);

        CommentReaction commentReaction = new CommentReaction(comment, member, Reaction.LIKE);
        commentReactionRepository.save(commentReaction);

        JSONObject requestBody = new JSONObject(Map.of("reactionName", "like"));
        CommentReactionExistsException exception = new CommentReactionExistsException();

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .post("api/comments/" + bookmark.getId() + "/reactions")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            assertions.assertThat(response.jsonPath().getString("message")).isEqualTo(exception.getMessage());
        });
    }

    @Test
    @DisplayName("Comment의 reaction을 삭제한다.")
    void deleteCommentReaction() {
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

        Comment comment = TestDataFactory.createComment(bookmark, member);
        commentRepository.save(comment);

        CommentReaction commentReaction = new CommentReaction(comment, member, Reaction.LIKE);
        commentReactionRepository.save(commentReaction);

        JSONObject requestBody = new JSONObject(Map.of("reactionName", "like"));

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .delete("api/comments/" + comment.getId() + "/reactions")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Comment에 없는 reaction을 삭제하려하면 예외가 발생한다.")
    void deleteUnsavedCommentReaction() {
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

        Comment comment = TestDataFactory.createComment(bookmark, member);
        commentRepository.save(comment);

        CommentReactionNotFoundException exception = new CommentReactionNotFoundException();
        JSONObject requestBody = new JSONObject(Map.of("reactionName", "like"));

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .delete("api/comments/" + comment.getId() + "/reactions")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(response.statusCode()).isEqualTo(exception.getStatus().value());
            assertions.assertThat(response.jsonPath().getString("message")).isEqualTo(exception.getMessage());
        });
    }

}
