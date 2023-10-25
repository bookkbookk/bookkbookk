package codesquad.bookkbookk.domain.bookclub.data.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateInvitationUrlRequest {

    private Long bookClubId;
    private String password;

    public CreateInvitationUrlRequest(Long bookClubId, String password) {
        this.bookClubId = bookClubId;
        this.password = password;
    }

}
