package codesquad.bookkbookk.integration.documentation;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;

import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

import codesquad.bookkbookk.common.error.exception.CommentNotFoundException;
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
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;
import codesquad.bookkbookk.domain.mapping.entity.CommentReaction;
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.mapping.repository.CommentReactionRepository;
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

public class CommentDocumentationTest extends IntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookClubRepository bookClubRepository;

    @Autowired
    private BookClubMemberRepository bookClubMemberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentReactionRepository commentReactionRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("코멘트를 생성한다.")
    void createComment() {
        // given
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

        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        bookmarkRepository.save(bookmark);

        JSONObject requestBody = new JSONObject(Map.of("bookmarkId", bookmark.getId(),
                "content", "content"));

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .filter(document("{class-name}/{method-name}",
                        requestFields(
                                fieldWithPath("bookmarkId").type(JsonFieldType.NUMBER).description("코멘트 생성할 북마크 아이디"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("코멘트 내용")
                        )))
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
    void updateComment() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook(bookClub);
        bookRepository.save(book);

        Chapter chapter = TestDataFactory.createChapter(book);
        chapterRepository.save(chapter);

        Topic topic = TestDataFactory.createTopic(chapter);
        topicRepository.save(topic);

        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        bookmarkRepository.save(bookmark);

        Comment comment = TestDataFactory.createComment(bookmark, member);
        commentRepository.save(comment);

        JSONObject requestBody = new JSONObject(Map.of("content", "updated content"));

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("commentId", comment.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("commentId").description("수정할 코멘트 아이디")
                        ),
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("코멘트 내용")
                        )))
                .when()
                .patch("api/comments/{commentId}")
                .then().log().all()
                .extract();

        // then
        Comment actual = commentRepository.findById(comment.getId()).orElseThrow(CommentNotFoundException::new);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(actual.getContents()).isEqualTo("updated content");
            softAssertions.assertThat(actual.getUpdatedTime()).isNotEqualTo(actual.getCreatedTime());
        });
    }

    @Test
    @DisplayName("코멘트를 삭제한다.")
    void deleteComment() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook(bookClub);
        bookRepository.save(book);

        Chapter chapter = TestDataFactory.createChapter(book);
        chapterRepository.save(chapter);

        Topic topic = TestDataFactory.createTopic(chapter);
        topicRepository.save(topic);

        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        bookmarkRepository.save(bookmark);

        Comment comment = TestDataFactory.createComment(bookmark, member);
        commentRepository.save(comment);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("commentId", comment.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("commentId").description("삭제할 코멘트 아이디")
                        )))
                .when()
                .delete("api/comments/{commentId}")
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
    @DisplayName("코멘트의 리액션을 생성한다.")
    void createCommentReaction() {
        // given
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

        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        bookmarkRepository.save(bookmark);

        Comment comment = TestDataFactory.createComment(bookmark, member);
        commentRepository.save(comment);

        JSONObject requestBody = new JSONObject(Map.of("reactionName", "like"));

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("commentId", comment.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("commentId").description("리액션 생성할 코멘트 아이디")
                        ),
                        requestFields(
                                fieldWithPath("reactionName").type(JsonFieldType.STRING).description("리액션 이름")
                        )))
                .when()
                .post("api/comments/{commentId}/reactions")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("코멘트의 리액션을 삭제한다.")
    void deleteCommentReaction() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook(bookClub);
        bookRepository.save(book);

        Chapter chapter = TestDataFactory.createChapter(book);
        chapterRepository.save(chapter);

        Topic topic = TestDataFactory.createTopic(chapter);
        topicRepository.save(topic);

        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        bookmarkRepository.save(bookmark);

        Comment comment = TestDataFactory.createComment(bookmark, member);
        commentRepository.save(comment);

        CommentReaction commentReaction = new CommentReaction(comment, member, Reaction.LIKE);
        commentReactionRepository.save(commentReaction);

        JSONObject requestBody = new JSONObject(Map.of("reactionName", "like"));

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .pathParam("commentId", comment.getId())
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("commentId").description("리액션 삭제할 코멘트 아이디")
                        ),
                        requestFields(
                                fieldWithPath("reactionName").type(JsonFieldType.STRING).description("리액션 이름")
                        )))
                .when()
                .delete("api/comments/{commentId}/reactions")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("코멘트의 리액션들을 가져온다.")
    void readCommentReactions() {
        // given
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

        Bookmark bookmark = TestDataFactory.createBookmark(member, topic);
        bookmarkRepository.save(bookmark);

        Comment comment = TestDataFactory.createComment(bookmark, member);
        commentRepository.save(comment);

        CommentReaction commentReaction1 = new CommentReaction(comment, member, Reaction.LIKE);
        commentReactionRepository.save(commentReaction1);
        CommentReaction commentReaction2 = new CommentReaction(comment, member, Reaction.CONGRATULATION);
        commentReactionRepository.save(commentReaction2);
        CommentReaction commentReaction3 = new CommentReaction(comment, member, Reaction.ROCKET);
        commentReactionRepository.save(commentReaction3);
        CommentReaction commentReaction4 = new CommentReaction(comment, member, Reaction.LIKE);
        commentReactionRepository.save(commentReaction4);
        CommentReaction commentReaction5 = new CommentReaction(comment, member, Reaction.LOVE);
        commentReactionRepository.save(commentReaction5);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("commentId", comment.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("commentId").description("리액션 조회할 코멘트 아이디")
                        ),
                        responseFields(
                                fieldWithPath("like").type(JsonFieldType.ARRAY)
                                        .description("\'좋아요\' 리액션한 멤버 닉네임들").optional(),
                                fieldWithPath("love").type(JsonFieldType.ARRAY)
                                        .description("\'하트\' 리액션한 멤버 닉네임들").optional(),
                                fieldWithPath("clap").type(JsonFieldType.ARRAY)
                                        .description("\'박수\' 리액션한 멤버 닉네임들").optional(),
                                fieldWithPath("congratulation").type(JsonFieldType.ARRAY).
                                        description("\'축하\' 리액션한 멤버 닉네임들").optional(),
                                fieldWithPath("rocket").type(JsonFieldType.ARRAY)
                                        .description("\'로켓\' 리액션한 멤버 닉네임들").optional()
                        )))
                .when()
                .get("api/comments/{commentId}/reactions")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getMap("").size()).isEqualTo(4);
            softAssertions.assertThat(response.jsonPath().getList("like").size()).isEqualTo(2);
            softAssertions.assertThat(response.jsonPath().getString("love")).isNotNull();
            softAssertions.assertThat(response.jsonPath().getString("clap")).isNull();
        });
    }

}
