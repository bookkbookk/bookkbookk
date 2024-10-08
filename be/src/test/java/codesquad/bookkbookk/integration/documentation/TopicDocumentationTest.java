package codesquad.bookkbookk.integration.documentation;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.jdbc.Sql;

import codesquad.bookkbookk.common.error.exception.TopicNotFoundException;
import codesquad.bookkbookk.common.jwt.JwtProvider;
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
import codesquad.bookkbookk.domain.topic.data.dto.UpdateTopicTitleRequest;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;
import codesquad.bookkbookk.domain.topic.repository.TopicRepository;
import codesquad.bookkbookk.util.IntegrationTest;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class TopicDocumentationTest extends IntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookClubRepository bookClubRepository;

    @Autowired
    private BookClubMemberRepository bookClubMemberRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @DisplayName("토픽을 생성한다.")
    @Test
    void createTopic(){
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

        Chapter chapter = new Chapter(book, "first");
        chapterRepository.save(chapter);

        String requestBody = "{\"chapterId\": 1, \"title\": \"토픽\"}";
        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .body(requestBody)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .filter(document("{class-name}/{method-name}",
                        requestFields(
                                fieldWithPath("chapterId").type(JsonFieldType.NUMBER).description("챕터 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("토픽 제목")
                        ),
                        responseFields(
                                fieldWithPath("createdTopicId").type(JsonFieldType.NUMBER).description("생성된 토픽 아이디")
                        )))
                .when()
                .post("/api/topics")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getLong("createdTopicId")).isNotZero();
        });

    }

    @DisplayName("토픽의 북마크들을 조회한다.")
    @Test
    void readTopicBookmarks(){
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

        Chapter chapter = new Chapter(book, "first");
        chapterRepository.save(chapter);

        Topic topic = new Topic(chapter, "topic");
        topicRepository.save(topic);

        List<Bookmark> bookmarks = TestDataFactory.createBookmarks(5, member, topic);
        bookmarkRepository.saveAll(bookmarks);

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("topicId", topic.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("topicId").description("조회할 토픽 아이디")
                        ),
                        responseFields(
                                fieldWithPath("[].bookmarkId").type(JsonFieldType.NUMBER).description("북마크 아이디"),
                                fieldWithPath("[].author").type(JsonFieldType.OBJECT).description("북마크 작성자"),
                                fieldWithPath("[].author.memberId")
                                        .type(JsonFieldType.NUMBER).description("북마크 작성자 멈버 아이디"),
                                fieldWithPath("[].author.nickname")
                                        .type(JsonFieldType.STRING).description("북마크 작성자 닉네임"),
                                fieldWithPath("[].author.profileImageUrl").
                                        type(JsonFieldType.STRING).description("북마크 작성자 프로필 이미지 URL"),
                                fieldWithPath("[].page").type(JsonFieldType.NUMBER).description("북마크 페이지"),
                                fieldWithPath("[].createdTime").type(JsonFieldType.STRING).description("북마크 생성 시간"),
                                fieldWithPath("[].updatedTime").type(JsonFieldType.STRING).description("북마크 수정 시간"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("북마크 내용")
                        )))
                .when()
                .get("/api/topics/{topicId}/bookmarks")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getList("").size()).isEqualTo(5);
            softAssertions.assertThat(response.jsonPath().getString("[1].author.nickname"))
                    .isEqualTo("nickname");
            softAssertions.assertThat(response.jsonPath().getLong("[3].bookmarkId"))
                    .isEqualTo(4);
        });
    }

    @DisplayName("토픽의 북마크 슬라이스를 조회한다.")
    @Test
    @Sql("classpath:sql/readTopicBookmarkSlice.sql")
    void readTopicBookmarkSlice() {
        // given
        Long memberId = 1L;
        Long topicId = 1L;
        String accessToken = jwtProvider.createAccessToken(memberId);

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("topicId", topicId)
                .queryParam("cursor", 0)
                .queryParam("size", 3)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("topicId").description("조회할 토픽 아이디")
                        ),
                        requestParameters(
                                parameterWithName("cursor").description("커서"),
                                parameterWithName("size").description("조회할 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("북마크 스크롤 가능 여부"),
                                fieldWithPath("bookmarks[]").type(JsonFieldType.ARRAY).description("북마크 슬라이스 북마크 정보"),
                                fieldWithPath("bookmarks[].bookmarkId").type(JsonFieldType.NUMBER).description("북마크 아이디"),
                                fieldWithPath("bookmarks[].author").type(JsonFieldType.OBJECT).description("북마크 작성자"),
                                fieldWithPath("bookmarks[].author.memberId")
                                        .type(JsonFieldType.NUMBER).description("북마크 작성자 멈버 아이디"),
                                fieldWithPath("bookmarks[].author.nickname")
                                        .type(JsonFieldType.STRING).description("북마크 작성자 닉네임"),
                                fieldWithPath("bookmarks[].author.profileImageUrl").
                                        type(JsonFieldType.STRING).description("북마크 작성자 프로필 이미지 URL"),
                                fieldWithPath("bookmarks[].page").type(JsonFieldType.NUMBER).description("북마크 페이지"),
                                fieldWithPath("bookmarks[].createdTime")
                                        .type(JsonFieldType.STRING).description("북마크 생성 시간"),
                                fieldWithPath("bookmarks[].updatedTime")
                                        .type(JsonFieldType.STRING).description("북마크 수정 시간"),
                                fieldWithPath("bookmarks[].content").type(JsonFieldType.STRING).description("북마크 내용"),
                                fieldWithPath("bookmarks[].reactions").type(JsonFieldType.OBJECT).description("북마크 리액션들"),
                                fieldWithPath("bookmarks[].reactions.like[]").optional().ignored(),
                                fieldWithPath("bookmarks[].reactions.love[]").optional().ignored(),
                                fieldWithPath("bookmarks[].reactions.congratulation[]").optional().ignored(),
                                fieldWithPath("bookmarks[].reactions.rocket[]").optional().ignored(),
                                fieldWithPath("bookmarks[].reactions.clap[]").optional().ignored(),
                                fieldWithPath("bookmarks[].commentSlice")
                                        .type(JsonFieldType.OBJECT).description("북마크 코멘트 슬라이스"),
                                fieldWithPath("bookmarks[].commentSlice.hasNext")
                                        .type(JsonFieldType.BOOLEAN).description("코멘트 스크롤 가능 여부"),
                                fieldWithPath("bookmarks[].commentSlice.comments[]")
                                        .type(JsonFieldType.ARRAY).description("코멘트 정보"),
                                fieldWithPath("bookmarks[].commentSlice.comments[].commentId")
                                        .type(JsonFieldType.NUMBER).description("코멘트 아이디"),
                                fieldWithPath("bookmarks[].commentSlice.comments[].author")
                                        .type(JsonFieldType.OBJECT).description("코멘트 작성자"),
                                fieldWithPath("bookmarks[].commentSlice.comments[].author.memberId")
                                        .type(JsonFieldType.NUMBER).description("코멘트 작성자 멤버 아이디"),
                                fieldWithPath("bookmarks[].commentSlice.comments[].author.nickname")
                                        .type(JsonFieldType.STRING).description("코멘트 작성자 닉네임"),
                                fieldWithPath("bookmarks[].commentSlice.comments[].author.profileImageUrl")
                                        .type(JsonFieldType.STRING).description("코멘트 작성지 프로필 이미지"),
                                fieldWithPath("bookmarks[].commentSlice.comments[].createdTime")
                                        .type(JsonFieldType.STRING).description("코멘트 생성시간"),
                                fieldWithPath("bookmarks[].commentSlice.comments[].content")
                                        .type(JsonFieldType.STRING).description("코멘트 내용"),
                                fieldWithPath("bookmarks[].commentSlice.comments[].reactions")
                                        .type(JsonFieldType.OBJECT).description("코멘트 리액션"),
                                fieldWithPath("bookmarks[].commentSlice.comments[].reactions.like[]").optional().ignored(),
                                fieldWithPath("bookmarks[].commentSlice.comments[].reactions.love[]").optional().ignored(),
                                fieldWithPath("bookmarks[].commentSlice.comments[].reactions.congratulation[]").optional().ignored(),
                                fieldWithPath("bookmarks[].commentSlice.comments[].reactions.rocket[]").optional().ignored(),
                                fieldWithPath("bookmarks[].commentSlice.comments[].reactions.clap[]").optional().ignored()
                        )))
                .when()
                .get("/api/topics/{topicId}/bookmarks/slice")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        });
    }

    @DisplayName("토픽을 수정한다")
    @Test
    void updateTopic() {
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

        Chapter chapter = new Chapter(book, "first");
        chapterRepository.save(chapter);

        Topic topic = new Topic(chapter, "topic");
        topicRepository.save(topic);

        UpdateTopicTitleRequest request = new UpdateTopicTitleRequest("updated title");

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("topicId", topic.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("topicId").description("변경할 토픽 아이디")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("변경할 토픽 제목")
                        )))
                .when()
                .patch("/api/topics/{topicId}")
                .then().log().all()
                .extract();

        //then
        Topic result = topicRepository.findById(topic.getId()).orElseThrow(TopicNotFoundException::new);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(result.getTitle()).isEqualTo("updated title");
        });
    }

    @DisplayName("토픽을 삭제한다")
    @Test
    void deleteTopic() {
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

        Chapter chapter = new Chapter(book, "first");
        chapterRepository.save(chapter);

        Topic topic = new Topic(chapter, "topic");
        topicRepository.save(topic);

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("topicId", topic.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("topicId").description("삭제할 토픽 아이디")
                        )))
                .when()
                .delete("/api/topics/{topicId}")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(topicRepository.findById(topic.getId())).isEmpty();
        });
    }
}
