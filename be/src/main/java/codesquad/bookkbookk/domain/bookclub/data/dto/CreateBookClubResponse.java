package codesquad.bookkbookk.domain.bookclub.data.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateBookClubResponse {

    private final Long bookClubId;
    private final String invitationUrl;

}
