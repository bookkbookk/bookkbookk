package codesquad.bookkbookk.bookclub.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateBookClubResponse {

    private Long bookClubId;

    private CreateBookClubResponse(Long bookClubId) {
        this.bookClubId = bookClubId;
    }

    public static CreateBookClubResponse from(Long bookClubId) {
        return new CreateBookClubResponse(bookClubId);
    }

}
