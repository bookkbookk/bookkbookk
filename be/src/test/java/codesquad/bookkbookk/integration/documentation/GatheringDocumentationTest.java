package codesquad.bookkbookk.integration.documentation;

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

import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;
import codesquad.bookkbookk.domain.gathering.repository.GatheringRepository;
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.util.IntegrationTest;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class GatheringDocumentationTest extends IntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookClubRepository bookClubRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookClubMemberRepository bookClubMemberRepository;

    @Autowired
    private GatheringRepository gatheringRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("모임을 수정한다.")
    void updateGathering() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook(bookClub);
        bookRepository.save(book);

        bookClubMemberRepository.save(new BookClubMember(bookClub, member));

        Gathering gathering = TestDataFactory.createGathering(book);
        gatheringRepository.save(gathering);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        Map<String, String> request = Map.of("dateTime", "2023-12-25T13:30:00Z");
        JSONObject requestBody = new JSONObject(request);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("gatheringId", gathering.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("gatheringId").description("모임 아이디")
                        ),
                        requestFields(
                                fieldWithPath("dateTime")
                                        .type(JsonFieldType.STRING).description("변경할 모임 시간").optional(),
                                fieldWithPath("place")
                                        .type(JsonFieldType.STRING).description("변경할 모임 장소").optional()
                        ),
                        responseFields(
                                fieldWithPath("gatheringId").type(JsonFieldType.NUMBER).description("모임 아이디"),
                                fieldWithPath("dateTime")
                                        .type(JsonFieldType.STRING).description("변경된 모임 시간"),
                                fieldWithPath("place")
                                        .type(JsonFieldType.STRING).description("변경된 모임 장소")
                        )))
                .when()
                .patch("/api/gatherings/{gatheringId}")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertions.assertThat(response.jsonPath().getString("dateTime"))
                    .startsWith(request.get("dateTime"));
            assertions.assertThat(response.jsonPath().getString("place")).isEqualTo(gathering.getPlace());
        });
    }

    @Test
    @DisplayName("모임을 삭제한다.")
    void deleteGathering() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        Book book = TestDataFactory.createBook(bookClub);
        bookRepository.save(book);

        bookClubMemberRepository.save(new BookClubMember(bookClub, member));

        Gathering gathering = TestDataFactory.createGathering(book);
        gatheringRepository.save(gathering);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .pathParam("gatheringId", gathering.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("gatheringId").description("모임 아이디")
                        )))
                .when()
                .delete("/api/gatherings/{gatheringId}")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        });
    }

}
