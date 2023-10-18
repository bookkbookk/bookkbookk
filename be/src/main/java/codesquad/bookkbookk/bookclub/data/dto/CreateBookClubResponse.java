package codesquad.bookkbookk.bookclub.data.dto;

import lombok.Getter;

@Getter
public class CreateBookClubResponse {

    private final Long bookClubId;

    private CreateBookClubResponse(Long bookClubId) {
        this.bookClubId = bookClubId;
    }

    public static CreateBookClubResponse from(Long bookClubId) {
        return new CreateBookClubResponse(bookClubId);
    }

}
