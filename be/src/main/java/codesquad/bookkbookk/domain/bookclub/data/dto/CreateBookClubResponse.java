package codesquad.bookkbookk.domain.bookclub.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateBookClubResponse {

    private Long bookClubId;

    public CreateBookClubResponse(Long bookClubId) {
        this.bookClubId = bookClubId;
    }

}
