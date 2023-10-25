package codesquad.bookkbookk.domain.bookclub.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateInvitationUrlRequest {

    private Long bookClubId;

    public CreateInvitationUrlRequest(Long bookClubId) {
        this.bookClubId = bookClubId;
    }

}
