package codesquad.bookkbookk.domain.bookclub.data.dto;

import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JoinBookClubResponse {

    private final Long bookClubId;

    public static JoinBookClubResponse from(BookClubMember bookClubMember) {
        return new JoinBookClubResponse(bookClubMember.getBookClub().getId());
    }

}
