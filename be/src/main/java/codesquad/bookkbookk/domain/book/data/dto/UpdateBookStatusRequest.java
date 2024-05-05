package codesquad.bookkbookk.domain.book.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UpdateBookStatusRequest {

    private Integer statusId;

}
