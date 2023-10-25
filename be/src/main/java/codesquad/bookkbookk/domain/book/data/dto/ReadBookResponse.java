package codesquad.bookkbookk.domain.book.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReadBookResponse {

    private final Pagination pagination;
    private final List<BookResponse> books;

    public static ReadBookResponse from(Page<Book> books) {
        return new ReadBookResponse(Pagination.from(books), BookResponse.from(books.getContent()));
    }

    @Getter
    public static class Pagination {

        private static final int PAGE_TO_INDEX_NUMBER = 1;

        private final long totalItemCounts;
        private final int totalPageCounts;
        private final int currentPageIndex;

        private Pagination(long totalItemCounts, int totalPageCounts, int currentPageIndex) {
            this.totalItemCounts = totalItemCounts;
            this.totalPageCounts = totalPageCounts;
            this.currentPageIndex = currentPageIndex;
        }

        public static Pagination from(Page<Book> books) {
            return new Pagination(books.getTotalElements(), books.getTotalPages(),
                    books.getNumber() + PAGE_TO_INDEX_NUMBER);
        }

    }

    @Getter
    public static class BookResponse {

        private final Long id;
        private final String isbn;
        private final BookClubResponse bookClub;
        private final String title;
        private final String cover;
        private final String author;
        private final String category;

        @Builder
        private BookResponse(Long id, String isbn, BookClubResponse bookClubResponse, String title,
                             String cover, String author, String category) {
            this.id = id;
            this.isbn = isbn;
            this.bookClub = bookClubResponse;
            this.title = title;
            this.cover = cover;
            this.author = author;
            this.category = category;
        }

        private static BookResponse from(Book book) {
            return BookResponse.builder()
                    .id(book.getId())
                    .isbn(book.getIsbn())
                    .bookClubResponse(BookClubResponse.from(book.getBookClub()))
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

    @RequiredArgsConstructor
    @Getter
    public static class BookClubResponse {

        private final Long id;
        private final String name;

        public static BookClubResponse from(BookClub bookClub) {
            return new BookClubResponse(bookClub.getId(), bookClub.getName());
        }

    }

}
