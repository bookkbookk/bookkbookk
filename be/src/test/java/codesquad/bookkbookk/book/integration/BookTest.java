package codesquad.bookkbookk.book.integration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.IntegrationTest;
import codesquad.bookkbookk.common.error.exception.ApiException;
import codesquad.bookkbookk.common.error.exception.BookNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.common.jwt.JwtProvider;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookRequest;
import codesquad.bookkbookk.domain.book.data.dto.CreateBookResponse;
import codesquad.bookkbookk.domain.book.data.dto.ReadBookResponse;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.data.entity.MemberBook;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.book.repository.MemberBookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.data.entity.MemberBookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.bookclub.repository.MemberBookClubRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.util.TestDataFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class BookTest extends IntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookClubRepository bookClubRepository;

    @Autowired
    private MemberBookClubRepository memberBookClubRepository;

    @Autowired
    private MemberBookRepository memberBookRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("책을 성공적으로 생성한다.")
    void createBook() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        MemberBookClub memberBookClub = new MemberBookClub(member, bookClub);
        memberBookClubRepository.save(memberBookClub);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        CreateBookRequest createBookRequest = CreateBookRequest.builder()
                .isbn("123123123")
                .bookClubId(bookClub.getId())
                .title("책")
                .cover("image.image")
                .author("감귤")
                .category("추리")
                .build();
        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .body(createBookRequest)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .post("/api/books")
                .then().log().all()
                .extract();

        //then
        CreateBookResponse createBookResponse = response.jsonPath().getObject("", CreateBookResponse.class);
        Book expectedBook = bookRepository.findById(createBookResponse.getCreatedBookId())
                .orElseThrow(BookNotFoundException::new);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(expectedBook.getIsbn()).isEqualTo(createBookRequest.getIsbn());
            softAssertions.assertThat(expectedBook.getCategory()).isEqualTo(createBookRequest.getCategory());
            softAssertions.assertThat(expectedBook.getTitle()).isEqualTo(createBookRequest.getTitle());
        });
    }

    @Test
    @DisplayName("책 추가 권한이 없는 멤버가 책을 추가했을 때 에러가 발생한다.")
    void createBookNotInBookClub() {
        //given
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        CreateBookRequest createBookRequest = CreateBookRequest.builder()
                .isbn("123123123")
                .bookClubId(bookClub.getId())
                .title("책")
                .cover("image.image")
                .author("감귤")
                .category("추리")
                .build();

        MemberNotInBookClubException exception = new MemberNotInBookClubException();
        //when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .body(createBookRequest)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .post("/api/books")
                .then().log().all()
                .extract();

        //then

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(exception.getCode());
            softAssertions.assertThat(response.jsonPath().getObject("", ApiException.class).getMessage())
                    .isEqualTo(exception.getMessage());
        });
    }

    @Test
    @DisplayName("요청한 멤버가 참여한 책들을 title 기준 오름차순으로 조회한다.")
    void readBooks() {
        Member member = TestDataFactory.createMember();
        memberRepository.save(member);

        BookClub bookClub = TestDataFactory.createBookClub();
        bookClubRepository.save(bookClub);

        Book book1 = TestDataFactory.createBook1();
        bookRepository.save(book1);
        Book book2 = TestDataFactory.createBook2();
        bookRepository.save(book2);

        MemberBook memberBook1 = new MemberBook(member, book1);
        memberBookRepository.save(memberBook1);

        MemberBook memberBook2 = new MemberBook(member, book2);
        memberBookRepository.save(memberBook2);

        String accessToken = jwtProvider.createAccessToken(member.getId());

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("page", 0)
                .queryParam("size", 1)
                .when()
                .get("/api/books")
                .then().log().all()
                .extract();

        ReadBookResponse result = response.jsonPath().getObject("", ReadBookResponse.class);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(result.getBooks().get(0).getTitle()).isEqualTo("개똥벌레");
            softAssertions.assertThat(result.getHasNext()).isEqualTo(true);
        });
    }

}
