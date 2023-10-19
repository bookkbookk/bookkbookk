package codesquad.bookkbookk.domain.book.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateBookRequest {

    private Long id;
    private Long bookClubId;
    private String title;
    private String cover;
    private String author;
    private String category;

    public CreateBookRequest(Long id, Long bookClubId, String title, String cover, String author, String category) {
        this.id = id;
        this.bookClubId = bookClubId;
        this.title = title;
        this.cover = cover;
        this.author = author;
        this.category = category;
    }

}
