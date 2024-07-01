package codesquad.bookkbookk.domain.bookclub.data.dto;

import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JoinBookClubResponse {

    private final Long bookClubId;

    public static JoinBookClubResponse from(BookClub bookClub) {
        return new JoinBookClubResponse(bookClub.getId());
    }

}
