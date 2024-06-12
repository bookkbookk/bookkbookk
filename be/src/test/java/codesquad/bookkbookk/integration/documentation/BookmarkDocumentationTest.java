package codesquad.bookkbookk.integration.documentation;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;

import java.util.List;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

import codesquad.bookkbookk.common.error.exception.BookmarkNotFoundException;
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
import codesquad.bookkbookk.domain.mapping.entity.BookmarkReaction;
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.mapping.repository.BookmarkReactionRepository;
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

public class BookmarkDocumentationTest extends IntegrationTest {

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
    private BookmarkReactionRepository bookmarkReactionRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("북마크를 생성한다.")
    void createBookmark() {
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

        JSONObject requestBody = new JSONObject(Map.of("topicId", topic.getId(),
                "page", 12,
                "content", "content"));

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .filter(document("{class-name}/{method-name}",
                        requestFields(
                                fieldWithPath("topicId").type(JsonFieldType.NUMBER).description("토픽 아이디"),
                                fieldWithPath("page").type(JsonFieldType.NUMBER).description("북마크 페이지"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("북마크 내용")
                        )))
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

        JSONObject requestBody = new JSONObject(Map.of("page", 123,
                "content", "updated content"));

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("bookmarkId", bookmark.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookmarkId").description("수정할 북마크 아이디")
                        ),
                        requestFields(
                                fieldWithPath("page").type(JsonFieldType.NUMBER).description("수정할 북마크 페이지"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 북마크 내용")
                        )))
                .when()
                .patch("api/bookmarks/{bookmarkId}")
                .then()
                .extract();

        // then
        Bookmark actual = bookmarkRepository.findById(bookmark.getId()).orElseThrow(BookmarkNotFoundException::new);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(actual.getPage()).isEqualTo(123);
            softAssertions.assertThat(actual.getUpdatedTime()).isNotEqualTo(actual.getCreatedTime());
        });
    }

    @Test
    @DisplayName("북마크를 삭제한다.")
    void deleteBookmark() {
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

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("bookmarkId", bookmark.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookmarkId").description("삭제할 북마크 아이디")
                        )))
                .when()
                .delete("api/bookmarks/{bookmarkId}")
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
    @DisplayName("북마크의 리액션을 생성한다.")
    void createBookmarkReaction() {
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

        JSONObject requestBody = new JSONObject(Map.of("reactionName", "like"));

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("bookmarkId", bookmark.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookmarkId").description("리액션할 북마크 아이디")
                        ),
                        requestFields(
                                fieldWithPath("reactionName").type(JsonFieldType.STRING).description("리액션 이름")
                        )))
                .when()
                .post("api/bookmarks/{bookmarkId}/reactions")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("북마크의 리액션을 삭제한다.")
    void deleteBookmarkReaction() {
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

        BookmarkReaction bookmarkReaction = new BookmarkReaction(bookmark, member, Reaction.LIKE);
        bookmarkReactionRepository.save(bookmarkReaction);

        JSONObject requestBody = new JSONObject(Map.of("reactionName", "like"));

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("bookmarkId", bookmark.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookmarkId").description("리액션 삭제할 북마크 아이디")
                        ),
                        requestFields(
                                fieldWithPath("reactionName").type(JsonFieldType.STRING).description("리액션 이름")
                        )))
                .when()
                .delete("api/bookmarks/{bookmarkId}/reactions")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("북마크의 코멘트들을 가져온다.")
    void readBookmarkComments() {
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

        List<Comment> comments = TestDataFactory.createComments(2, bookmark, member);
        commentRepository.saveAll(comments);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("bookmarkId", bookmark.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookmarkId").description("조회할 북마크 아이디")
                        ),
                        responseFields(
                                fieldWithPath("[].commentId").type(JsonFieldType.NUMBER).description("코멘트 아이디"),
                                fieldWithPath("[].author").type(JsonFieldType.OBJECT).description("코멘트 작성자"),
                                fieldWithPath("[].author.memberId")
                                        .type(JsonFieldType.NUMBER).description("코멘트 작성자 멤버 아이디"),
                                fieldWithPath("[].author.nickname")
                                        .type(JsonFieldType.STRING).description("코멘트 작성자 닉네임"),
                                fieldWithPath("[].author.profileImgUrl")
                                        .type(JsonFieldType.STRING).description("코멘트 작성자 프로필 이미지 URL"),
                                fieldWithPath("[].createdTime").type(JsonFieldType.STRING).description("코멘트 생성 시간"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("코멘트 내용")
                        )))
                .when()
                .get("api/bookmarks/{bookmarkId}/comments")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getList("$").size()).isEqualTo(2);
        });
    }

    @Test
    @DisplayName("북마크의 리액션들을 가져온다.")
    void readBookmarkReactions() {
        // given
        List<Member> members = TestDataFactory.createMembers(2);
        memberRepository.saveAll(members);
        Member member = members.get(0);
        Member anotherMember = members.get(1);

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

        BookmarkReaction bookmarkReaction1 = new BookmarkReaction(bookmark, member, Reaction.LIKE);
        bookmarkReactionRepository.save(bookmarkReaction1);
        BookmarkReaction bookmarkReaction2 = new BookmarkReaction(bookmark, member, Reaction.CONGRATULATION);
        bookmarkReactionRepository.save(bookmarkReaction2);
        BookmarkReaction bookmarkReaction3 = new BookmarkReaction(bookmark, member, Reaction.ROCKET);
        bookmarkReactionRepository.save(bookmarkReaction3);
        BookmarkReaction bookmarkReaction4 = new BookmarkReaction(bookmark, anotherMember, Reaction.LIKE);
        bookmarkReactionRepository.save(bookmarkReaction4);
        BookmarkReaction bookmarkReaction5 = new BookmarkReaction(bookmark, anotherMember, Reaction.LOVE);
        bookmarkReactionRepository.save(bookmarkReaction5);


        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("bookmarkId", bookmark.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("bookmarkId").description("조회할 북마크 아이디")
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
                .get("api/bookmarks/{bookmarkId}/reactions")
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
