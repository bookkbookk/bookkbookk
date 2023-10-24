package codesquad.bookkbookk.domain.book.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.domain.book.data.entity.Book;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadBookResponse {

    private List<BookResponse> books;
    private Boolean hasNext;

    public ReadBookResponse(List<Book> books, Boolean hasNext) {
        this.books = BookResponse.from(books);
        this.hasNext = hasNext;
    }

    @Getter
    public static class BookResponse {

        private final Long id;
        private final String isbn;
        private final Long bookClubId;
        private final String title;
        private final String cover;
        private final String author;
        private final String category;

        @Builder
        private BookResponse(Long id, String isbn, Long bookClubId, String title,
                             String cover, String author, String category) {
            this.id = id;
            this.isbn = isbn;
            this.bookClubId = bookClubId;
            this.title = title;
            this.cover = cover;
            this.author = author;
            this.category = category;
        }

        private static BookResponse from(Book book) {
            return BookResponse.builder()
                    .id(book.getId())
                    .isbn(book.getIsbn())
                    .bookClubId(book.getBookClubId())
                    .title(book.getTitle())
                    .cover(book.getCover())
                    .author(book.getAuthor())
                    .category(book.getCategory())
                    .build();
        }

        public static List<BookResponse> from(List<Book> books) {
            return books.stream()
                    .map(BookResponse::from)
                    .collect(Collectors.toUnmodifiableList());
        }

    }

}
