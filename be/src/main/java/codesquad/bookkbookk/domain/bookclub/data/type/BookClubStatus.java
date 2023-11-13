package codesquad.bookkbookk.domain.bookclub.data.type;

import codesquad.bookkbookk.domain.bookclub.data.dto.ClosedBookClubDetailResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.OpenBookClubDetailResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.ReadBookClubDetailResponse;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;

public enum BookClubStatus {

    OPEN {
        @Override
        public OpenBookClubDetailResponse from(BookClub bookClub) {
            return OpenBookClubDetailResponse.from(bookClub);
        }
    },
    CLOSED {
        @Override
        public ClosedBookClubDetailResponse from(BookClub bookClub) {
            return ClosedBookClubDetailResponse.from(bookClub);
        }
    };

    public abstract ReadBookClubDetailResponse from(BookClub bookClub);

}
