package codesquad.bookkbookk.topic.integration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.domain.topic.data.dto.CreateTopicRequest;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;
import codesquad.bookkbookk.domain.topic.repository.TopicRepository;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class TopicTest extends IntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @DisplayName("성공적으로 토픽을 생성한다.")
    @Test
    void createTopic(){
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        CreateTopicRequest createTopicRequest = new CreateTopicRequest(1L, "토픽1");

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                    .body(createTopicRequest)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .contentType(ContentType.JSON)
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

    @DisplayName("성공적으로 토픽을 조회한다")
    @Test
    void readTopicList(){
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        Topic topic1 = TestDataFactory.createTopic(1L, "토픽1");
        Topic topic2 = TestDataFactory.createTopic(1L, "토픽2");
        topicRepository.save(topic1);
        topicRepository.save(topic2);

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                    .get("/api/topics/1")
                .then().log().all()
                    .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getList("")).hasSize(2);
        });
    }
}
