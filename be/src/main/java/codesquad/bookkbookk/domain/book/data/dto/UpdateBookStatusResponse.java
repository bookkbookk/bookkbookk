package codesquad.bookkbookk.domain.book.data.dto;

import codesquad.bookkbookk.domain.book.data.entity.Book;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateBookStatusResponse {

    private final Long bookId;
    private final Integer statusId;

    public static UpdateBookStatusResponse from(Book book) {
        return new UpdateBookStatusResponse(book.getId(), book.getStatus().getId());
    }

}
