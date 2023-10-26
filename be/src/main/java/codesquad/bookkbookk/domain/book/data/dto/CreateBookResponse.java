package codesquad.bookkbookk.domain.book.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateBookResponse {

    private Long createdBookId;

    public CreateBookResponse(Long createdBookId) {
        this.createdBookId = createdBookId;
    }

}
