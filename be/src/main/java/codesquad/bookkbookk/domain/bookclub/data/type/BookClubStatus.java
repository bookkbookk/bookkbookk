package codesquad.bookkbookk.domain.bookclub.data.type;

import java.util.List;

import codesquad.bookkbookk.common.error.exception.BookClubStatusNotFoundException;
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

        @Override
        public List<ReadBookClubDetailResponse> from(List<BookClub> bookClubs) {
            return OpenBookClubDetailResponse.from(bookClubs);
        }
    },
    CLOSED {
        @Override
        public ClosedBookClubDetailResponse from(BookClub bookClub) {
            return ClosedBookClubDetailResponse.from(bookClub);
        }

        @Override
        public List<ReadBookClubDetailResponse> from(List<BookClub> bookClubs) {
            return ClosedBookClubDetailResponse.from(bookClubs);
        }
    };

    public static BookClubStatus of(String name) {
        String upperCaseName = name.toUpperCase();

        for (BookClubStatus status : BookClubStatus.values()) {
            if (status.name().equals(upperCaseName)) {
                return status;
            }
        }
        throw new BookClubStatusNotFoundException();
    }

    public abstract ReadBookClubDetailResponse from(BookClub bookClub);

    public abstract List<ReadBookClubDetailResponse> from(List<BookClub> bookClubs);

}
