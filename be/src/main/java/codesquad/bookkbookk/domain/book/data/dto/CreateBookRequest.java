package codesquad.bookkbookk.domain.book.data.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateBookRequest {

    private Long isbn;
    private Long bookClubId;
    private String title;
    private String cover;
    private String author;
    private String category;

    @Builder
    private CreateBookRequest(Long isbn, Long bookClubId, String title, String cover, String author, String category) {
        this.isbn = isbn;
        this.bookClubId = bookClubId;
        this.title = title;
        this.cover = cover;
        this.author = author;
        this.category = category;
    }

}
