package codesquad.bookkbookk.integration.documentation;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;

import java.util.List;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

import codesquad.bookkbookk.common.error.exception.ChapterNotFoundException;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
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

public class ChapterDocumentationTest extends IntegrationTest {

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
    private JwtProvider jwtProvider;

    @DisplayName("챕터을 생성한다.")
    @Test
    void createChapter() throws JSONException {
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

        JSONObject requestBody = createRequestBody(book.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .body(requestBody.toString())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .filter(document("{class-name}/{method-name}",
                        requestFields(
                                fieldWithPath("bookId").type(JsonFieldType.NUMBER).description("책 아이디"),
                                fieldWithPath("chapters").type(JsonFieldType.ARRAY).description("생성할 챕터들"),
                                fieldWithPath("chapters[].title").type(JsonFieldType.STRING).description("생성할 챕터의 제목"),
                                fieldWithPath("chapters[].topics").type(JsonFieldType.ARRAY).description("생성할 챕터의 토픽들"),
                                fieldWithPath("chapters[].topics[].title").type(JsonFieldType.STRING).description("생성할 토픽의 제목")
                        ),
                        responseFields(
                                fieldWithPath("createdChapterIds").type(JsonFieldType.ARRAY).description("생성된 챕터 아이디들")
                        )))
                .when()
                .post("/api/chapters")
                .then().log().all()
                .extract();

        //then
        int chapterCount = requestBody.getJSONArray("chapters").length();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getList("createdChapterIds").size())
                    .isEqualTo(chapterCount);
        });
    }

    @DisplayName("챕터의 상태와 제목을 변경한다.")
    @Test
    void updateChapter() {
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

        JSONObject requestBody = new JSONObject(Map.of("title", "update", "statusId", 2));

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("chapterId", chapter.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("chapterId").description("챕터 아이디")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("변경할 제목").optional(),
                                fieldWithPath("statusId").type(JsonFieldType.NUMBER).description("변경할 상태 아이디").optional()
                        ),
                        responseFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("변경된 제목"),
                                fieldWithPath("statusId").type(JsonFieldType.NUMBER).description("변경된 상태 아이디")
                        )))
                .when()
                .patch("/api/chapters/{chapterId}")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getString("title")).isEqualTo("update");
            softAssertions.assertThat(response.jsonPath().getString("statusId")).isEqualTo("2");
        });
    }

    @DisplayName("챕터를 식제한다.")
    @Test
    void deleteChapter() {
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

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("chapterId", chapter.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("chapterId").description("챕터 아이디")
                        )))
                .when()
                .delete("/api/chapters/{chapterId}")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThatThrownBy(() -> chapterRepository.findById(chapter.getId())
                            .orElseThrow(ChapterNotFoundException::new))
                    .isInstanceOf(ChapterNotFoundException.class);
        });
    }

    @DisplayName("챕터의 토픽들을 조회한다")
    @Test
    void readChapterTopics(){
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

        List<Topic> topics = TestDataFactory.createTopics(2, chapter);
        topicRepository.saveAll(topics);

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("chapterId", chapter.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("chapterId").description("챕터 아이디")
                        ),
                        responseFields(
                                fieldWithPath("[].topicId").type(JsonFieldType.NUMBER).description("토픽 아이디"),
                                fieldWithPath("[].title").type(JsonFieldType.STRING).description("토픽 제목")
                        )))
                .when()
                .get("/api/chapters/{chapterId}/topics")
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getList("")).hasSize(2);
        });
    }

    private JSONObject createRequestBody(Long bookId) throws JSONException {
        JSONObject requestBody = new JSONObject();

        requestBody.put("bookId", bookId);

        JSONArray chaptersArray = new JSONArray();

        JSONObject chapter1 = new JSONObject();
        chapter1.put("title", "chapter 1");
        JSONArray topicsArray1 = new JSONArray();
        topicsArray1.put(new JSONObject(Map.of("title", "topic 1")));
        topicsArray1.put(new JSONObject(Map.of("title", "topic 2")));
        chapter1.put("topics", topicsArray1);
        chaptersArray.put(chapter1);

        JSONObject chapter2 = new JSONObject();
        chapter2.put("title", "chapter 2");
        JSONArray topicsArray2 = new JSONArray();
        topicsArray2.put(new JSONObject(Map.of("title", "topic 3")));
        chapter2.put("topics", topicsArray2);
        chaptersArray.put(chapter2);

        requestBody.put("chapters", chaptersArray);

        return requestBody;
    }

}
