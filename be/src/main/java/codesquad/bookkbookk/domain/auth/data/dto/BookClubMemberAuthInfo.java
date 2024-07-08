package codesquad.bookkbookk.domain.auth.data.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BookClubMemberAuthInfo {

    private final Long bookClubId;
    private final Long memberId;
    private final Long entityId;


}
