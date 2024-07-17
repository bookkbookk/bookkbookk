package codesquad.bookkbookk.domain.book.data.dto;

import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateBookRequest {

    private Long bookClubId;
    private String isbn;
    private String title;
    private String cover;
    private String author;
    private String category;

    @Builder
    private CreateBookRequest(String isbn, Long bookClubId, String title, String cover, String author, String category) {
        this.isbn = isbn;
        this.bookClubId = bookClubId;
        this.title = title;
        this.cover = cover;
        this.author = author;
        this.category = category;
    }

    public Book toBook(BookClub bookClub) {
        return Book.builder()
                .isbn(isbn)
                .bookClub(bookClub)
                .title(title)
                .cover(cover)
                .author(author)
                .category(category)
                .build();
    }

}
