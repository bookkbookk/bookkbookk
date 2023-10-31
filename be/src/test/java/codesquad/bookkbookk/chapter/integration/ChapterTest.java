package codesquad.bookkbookk.chapter.integration;

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

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.chapter.repository.ChapterRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;
import codesquad.bookkbookk.domain.topic.repository.TopicRepository;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ChapterTest extends IntegrationTest {

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
    JwtProvider jwtProvider;

    @DisplayName("성공적으로 챕터을 생성한다.")
    @Test
    void createChapter() throws JSONException {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook1(bookClub);
        bookRepository.save(book);

        JSONObject requestBody = createRequestBody(book.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                    .body(requestBody.toString())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .contentType(ContentType.JSON)
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

    @DisplayName("토픽을 조회한다.")
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

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .get("/api/chapters/" + book.getId())
                .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getString("[0].title"))
                    .isEqualTo(chapters.get(0).getTitle());
            softAssertions.assertThat(response.jsonPath().getInt("[1].topicsCount"))
                    .isEqualTo(chapters.get(1).getTopics().size());
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
