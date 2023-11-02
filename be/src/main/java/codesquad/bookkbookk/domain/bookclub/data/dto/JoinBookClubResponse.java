package codesquad.bookkbookk.domain.bookclub.data.dto;

import codesquad.bookkbookk.domain.bookclub.data.entity.MemberBookClub;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JoinBookClubResponse {

    private final Long bookClubId;

    public static JoinBookClubResponse from(MemberBookClub memberBookClub) {
        return new JoinBookClubResponse(memberBookClub.getBookClub().getId());
    }

}
