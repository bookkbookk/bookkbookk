package codesquad.bookkbookk.domain.book.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.domain.book.data.entity.Book;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReadBookResponse {

    private final Pagination pagination;
    private final List<BookResponse> books;

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

    @Getter
    @RequiredArgsConstructor
    public static class Pagination {

        private final int totalItemCounts;
        private final int totalPageCounts;
        private final int currentPageIndex;

    }

}
