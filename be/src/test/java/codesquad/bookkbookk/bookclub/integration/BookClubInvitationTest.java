package codesquad.bookkbookk.bookclub.integration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateInvitationUrlRequest;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClubInvitationUrl;
import codesquad.bookkbookk.domain.bookclub.data.entity.MemberBookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubInvitationUrlRepository;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.bookclub.repository.MemberBookClubRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class BookClubInvitationTest extends IntegrationTest {

    @Autowired
    private BookClubRepository bookClubRepository;

    @Autowired
    private MemberBookClubRepository memberBookClubRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookClubInvitationUrlRepository bookClubInvitationUrlRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @DisplayName("초대 url을 성공적으로 생성한다.")
    @Test
    void createInvitationUrl() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        MemberBookClub memberBookClub = new MemberBookClub(member, bookClub);
        memberBookClubRepository.save(memberBookClub);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        CreateInvitationUrlRequest request = new CreateInvitationUrlRequest(1L, "ddd");

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                    .body(request)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .contentType(ContentType.JSON)
                .when()
                    .post("/api/book-clubs/invitation")
                    .then().log().all()
                .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getString("invitationUrl")).startsWith("bookkbookk.site/join/");
        });
    }

    @DisplayName("초대 url을 성공적으로 조회한다.")
    @Test
    void readInvitationUrl() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        MemberBookClub memberBookClub = new MemberBookClub(member, bookClub);
        memberBookClubRepository.save(memberBookClub);

        CreateInvitationUrlRequest request = new CreateInvitationUrlRequest(1L, "ddd");
        String invitationUrl = "bookkbookk.site/join/test";
        BookClubInvitationUrl bookClubInvitationUrl = new BookClubInvitationUrl(request, invitationUrl);
        bookClubInvitationUrlRepository.save(bookClubInvitationUrl);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .queryParam("bookClubId", bookClub.getId())
                .when()
                    .get("/api/book-clubs/invitation")
                .then().log().all()
                    .extract();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getString("invitationUrl")).isEqualTo("bookkbookk.site/join/test");
        });
    }
}
