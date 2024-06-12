package codesquad.bookkbookk.integration.documentation;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.mapping.entity.MemberBook;
import codesquad.bookkbookk.domain.mapping.repository.MemberBookRepository;
import codesquad.bookkbookk.domain.member.data.dto.MemberResponse;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.util.IntegrationTest;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MemberDocumentationTest extends IntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookClubRepository bookClubRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberBookRepository memberBookRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("멤버 정보를 조회한다.")
    void readMember() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .filter(document("{class-name}/{method-name}",
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("멤버 아이디"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("멤버 이메일"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("멤버 닉네임"),
                                fieldWithPath("profileImgUrl").type(JsonFieldType.STRING).description("멤버 프로필 이미지 URL")
                        )))
                .when()
                .get("/api/members")
                .then().log().all()
                .extract();
        MemberResponse result = response.jsonPath().getObject("", MemberResponse.class);
        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(result.getId()).isEqualTo(member.getId());
            softAssertions.assertThat(result.getNickname()).isEqualTo(member.getNickname());
            softAssertions.assertThat(result.getEmail()).isEqualTo(member.getEmail());
            softAssertions.assertThat(result.getProfileImgUrl()).isEqualTo(member.getProfileImageUrl());
        });
    }

    @Test
    @DisplayName("멤버의 프로필을 성공적으로 수정한다.")
    void updateMember() throws IOException {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);
        String accessToken = jwtProvider.createAccessToken(member.getId());

        //when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .multiPart("profileImage", File.createTempFile("create", "jpeg")
                        , MediaType.IMAGE_JPEG_VALUE)
                .multiPart("nickname", "New nickname")
                .filter(document("{class-name}/{method-name}",
                        requestParts(
                                partWithName("profileImage").description("멤버 프로필 이미지 파일").optional(),
                                partWithName("nickname").description("변경할 닉네임").optional()
                        ),
                        responseFields(
                                fieldWithPath("newNickname").type(JsonFieldType.STRING).description("변경된 닉네임"),
                                fieldWithPath("newProfileImgUrl").type(JsonFieldType.STRING).description("변경된 프로필 이미지 URL")
                        )))
                .when()
                .patch("/api/members/profile")
                .then().log().all()
                .extract();

        //then
        Member result = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(result.getNickname()).isEqualTo("New nickname");
        });

    }

    @Test
    @DisplayName("요청한 멤버가 참여한 책들을 페이지로 나눠서 보내준다.")
    void readMemberBooks() {
        // given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub(member);
        bookClubRepository.save(bookClub);

        List<Book> books = TestDataFactory.createBooks(2, bookClub);
        bookRepository.saveAll(books);

        MemberBook memberBook1 = new MemberBook(member, books.get(0));
        memberBookRepository.save(memberBook1);
        MemberBook memberBook2 = new MemberBook(member, books.get(1));
        memberBookRepository.save(memberBook2);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        // when
        ExtractableResponse<Response> response = RestAssured
                .given(this.spec).log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("page", 0)
                .queryParam("size", 1)
                .filter(document("{class-name}/{method-name}",
                        requestParameters(
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 크기")
                        ),
                        responseFields(
                                fieldWithPath("pagination").type(JsonFieldType.OBJECT).description("페이징 정보"),
                                fieldWithPath("pagination.totalItemCounts")
                                        .type(JsonFieldType.NUMBER).description("총 책 개수"),
                                fieldWithPath("pagination.totalPageCounts")
                                        .type(JsonFieldType.NUMBER).description("총 페이지 개수"),
                                fieldWithPath("pagination.currentPageIndex").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("books").type(JsonFieldType.ARRAY).description("책 정보"),
                                fieldWithPath("books[].id").type(JsonFieldType.NUMBER).description("책 아이디"),
                                fieldWithPath("books[].statusId").type(JsonFieldType.NUMBER).description("책 상태 아이디"),
                                fieldWithPath("books[].isbn").type(JsonFieldType.STRING).description("책 ISBN"),
                                fieldWithPath("books[].bookClub").type(JsonFieldType.OBJECT).description("책의 북클럽 정보"),
                                fieldWithPath("books[].bookClub.id").type(JsonFieldType.NUMBER).description("북클럽 아이디"),
                                fieldWithPath("books[].bookClub.name")
                                        .type(JsonFieldType.STRING).description("북클럽 이름"),
                                fieldWithPath("books[].title").type(JsonFieldType.STRING).description("책 제목"),
                                fieldWithPath("books[].cover").type(JsonFieldType.STRING).description("책 표지 이미지 URL"),
                                fieldWithPath("books[].author").type(JsonFieldType.STRING).description("책 저자"),
                                fieldWithPath("books[].category").type(JsonFieldType.STRING).description("책 카테고리")
                        )))
                .when()
                .get("/api/members/books")
                .then().log().all()
                .extract();

        // then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(response.jsonPath().getMap("pagination").get("totalPageCounts"))
                    .isEqualTo(2);
            softAssertions.assertThat(((LinkedHashMap<?, ?>) response.jsonPath().getList("books").get(0)).get("title"))
                    .isEqualTo(books.get(0).getTitle());
            softAssertions.assertThat(((LinkedHashMap<?, ?>) response.jsonPath().getList("books").get(0)).get("statusId"))
                    .isEqualTo(books.get(0).getStatus().getId());
        });
    }

}
