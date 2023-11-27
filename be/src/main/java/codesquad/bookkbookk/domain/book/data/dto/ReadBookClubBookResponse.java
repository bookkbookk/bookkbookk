package codesquad.bookkbookk.domain.book.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;

import codesquad.bookkbookk.domain.book.data.entity.Book;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReadBookClubBookResponse {

    private final Boolean hasNext;
    private final List<BookResponse> books;

    public static ReadBookClubBookResponse from(Slice<Book> books) {
        return new ReadBookClubBookResponse(books.hasNext(), BookResponse.from(books));
    }

    @Getter
    public static class BookResponse {

        private final Long id;
        private final String title;
        private final String cover;
        private final String author;
        private final String category;

        @Builder
        private BookResponse(Long id, String title, String cover, String author, String category) {
            this.id = id;
            this.title = title;
            this.cover = cover;
            this.author = author;
            this.category = category;
        }

        public static List<BookResponse> from(Slice<Book> books) {
            return books.getContent().stream()
                    .map(BookResponse::from)
                    .collect(Collectors.toUnmodifiableList());
        }

        private static BookResponse from(Book book) {
            return BookResponse.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .cover(book.getCover())
                    .author(book.getAuthor())
                    .category(book.getCategory())
                    .build();
        }

    }

}
