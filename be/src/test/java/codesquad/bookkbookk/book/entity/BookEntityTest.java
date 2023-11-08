package codesquad.bookkbookk.book.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import codesquad.bookkbookk.common.error.exception.MalformedIsbnException;
import codesquad.bookkbookk.domain.book.data.entity.Book;

public class BookEntityTest {

    @Test
    @DisplayName("13자리 ISBN을 검증하고 Book을 만든다.")
    void validateISBN13AndCreateBook() {
        // given
        String isbn = "9788998139766";

        // when
        Book book = Book.builder()
                .isbn(isbn)
                .build();

        // then
        assertThat(book.getIsbn()).isEqualTo(isbn);

    }

    @Test
    @DisplayName("10자리 ISBN을 검증하고 Book을 만든다.")
    void validateISBN10AndCreateBook() {
        // given
        String isbn = "8985846418";

        // given & when & then
        Book book = Book.builder()
                .isbn(isbn)
                .build();

        // then
        assertThat(book.getIsbn()).isEqualTo(isbn);

    }

    @Test
    @DisplayName("마지막 문자가 'X'인 10자리 ISBN을 검증하고 Book을 만든다.")
    void validateLastCharacterXISBN10AndCreateBook() {
        // given
        String isbn = "897154211X";

        // when
        Book book = Book.builder()
                .isbn(isbn)
                .build();

        // then
        assertThat(book.getIsbn()).isEqualTo(isbn);
    }

    @Test
    @DisplayName("마지막 문자가 'x'인 10자리 ISBN을 검증하고 Book을 만든다.")
    void validateLastCharacterxISBN10AndCreateBook() {
        // given & when & then
        String isbn = "897154211x";

        // when
        Book book = Book.builder()
                .isbn(isbn)
                .build();

        // then
        assertThat(book.getIsbn()).isEqualTo(isbn.toUpperCase());
    }

    @Test
    @DisplayName("사이사이 \'-\'가 추가된 13자리 isbn으로 책을 만든다.")
    void createDashISBN13Book() {
        // given
        String isbn = "979-11-5839-140-9";

        // when
        Book book = Book.builder()
                .isbn(isbn)
                .build();

        // then
        String impliedISBN = isbn.replace("-", "");
        assertThat(book.getIsbn()).isEqualTo(impliedISBN);
    }

    @Test
    @DisplayName("사이사이 \'-\'가 추가된 10자리 isbn으로 책을 만든다.")
    void createDashISBN1Book() {
        // given
        String isbn = "89-85846-41-8";

        // when
        Book book = Book.builder()
                .isbn(isbn)
                .build();

        // then
        String impliedISBN = isbn.replace("-", "");
        assertThat(book.getIsbn()).isEqualTo(impliedISBN);
    }

    @Test
    @DisplayName("isbn10의 경우 끝자리에 \'X\', \'x\', \'-\'를 제외한 다른 문자가 들어가면 예외가 발생한다.")
    void createMalformedISBN10Book() {
        // given & when & then
        assertThatThrownBy(() -> Book.builder().isbn("898584641A").build())
                .isInstanceOf(MalformedIsbnException.class);
    }

    @Test
    @DisplayName("isbn13의 경우 \'-\'를 제외한 다른 문자가 들어가면 예외가 발생한다.")
    void createMalformedISBN13Book() {
        // given & when & then
        assertThatThrownBy(() -> Book.builder().isbn("978899813976*").build())
                .isInstanceOf(MalformedIsbnException.class);
    }

    @Test
    @DisplayName("isbn13으로 책을 만들 때 검증에 실패한다.")
    void failValidateingISBN13() {
        // given & when & then
        assertThatThrownBy(() -> Book.builder().isbn("9788998139760").build())
                .isInstanceOf(MalformedIsbnException.class);
    }

    @Test
    @DisplayName("isbn10으로 책을 만들 때 검증에 실패한다.")
    void failValidateingISBN10() {
        // given & when & then
        assertThatThrownBy(() -> Book.builder().isbn("8985846419").build())
                .isInstanceOf(MalformedIsbnException.class);
    }

}
