package codesquad.bookkbookk.gathering.integration;

import java.util.Map;

import org.assertj.core.api.SoftAssertions;
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
import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;
import codesquad.bookkbookk.domain.gathering.repository.GatheringRepository;
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class GatheringTest extends IntegrationTest {

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

        Map<String, String> request = Map.of("dateTime", "2023-12-30T01:30");
        JSONObject requestBody = new JSONObject(request);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .patch("/api/gatherings/" + gathering.getId())
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
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/gatherings/" + gathering.getId())
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        });
    }

}
